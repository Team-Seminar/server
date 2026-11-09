package com.example.server.domain.reservation;

import com.example.server.domain.reservation.presentation.ReservationController;
import com.example.server.domain.reservation.repository.ReservationClear;
import com.example.server.domain.reservation.repository.ReservationRepository;
import com.example.server.domain.reservationSlot.*;
import com.example.server.domain.reservationSlot.presentation.dto.CreateSlotDTO;
import com.example.server.domain.student.*;
import com.example.server.global.config.TimeConfig;
import com.example.server.global.error.GlobalExceptionHandler;
import com.example.server.global.error.exception.*;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.time.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReservationReviewTest {
    private final ReservationService service = mock(ReservationService.class);
    private final ReservationSlotRepository slots = mock(ReservationSlotRepository.class);
    private final Clock clock = Clock.fixed(Instant.parse("2026-11-09T13:00:00Z"), ZoneId.of(TimeConfig.ZONE));
    private final ReservationController controller = new ReservationController(service, null, null, clock, slots);

    private UsernamePasswordAuthenticationToken caller(String role) {
        return new UsernamePasswordAuthenticationToken("student-id", null,
                List.of(new SimpleGrantedAuthority(role)));
    }

    @Test void rejectsAnonymousAndNonMembersBeforeDeletion() {
        assertThrows(CustomException.class, () -> controller.DeleteRequest(1L, null));
        assertThrows(CustomException.class, () -> controller.DeleteRequest(1L,
                new AnonymousAuthenticationToken("key", "anonymousUser", List.of(new SimpleGrantedAuthority("ROLE_ANONYMOUS")))));
        CustomException error = assertThrows(CustomException.class,
                () -> controller.DeleteRequest(1L, caller("ROLE_STUDENT")));
        assertEquals(ErrorCode.NOT_ABLE_STUDENT, error.getCode());
        verifyNoInteractions(service);
    }

    @Test void allowsTeacherAndReservationMemberOnly() {
        controller.DeleteRequest(1L, caller("ROLE_TEACHER"));
        when(slots.existsByReservation_IdAndStudent_User_Id(2L, "student-id")).thenReturn(true);
        controller.DeleteRequest(2L, caller("ROLE_STUDENT"));
        verify(service).reservationDelete(1L);
        verify(service).reservationDelete(2L);
    }

    @Test void creationUsesSeoulClock() {
        ReservationRepository repository = mock(ReservationRepository.class);
        var classrooms = mock(com.example.server.domain.classroom.ClassroomRepository.class);
        when(classrooms.findById(1L)).thenReturn(Optional.of(
                com.example.server.domain.classroom.Classroom.builder().id(1L).floor(1).build()));
        doAnswer(invocation -> {
            Reservation reservation = invocation.getArgument(0);
            assertEquals(LocalDateTime.of(2026, 11, 9, 22, 0), reservation.getCreateAt());
            org.springframework.test.util.ReflectionTestUtils.setField(reservation, "id", 1L);
            return reservation;
        }).when(repository).save(any(Reservation.class));
        new ReservationService(repository, classrooms, slots, clock).reservationCreate(
                new com.example.server.domain.reservation.presentation.dto.ReservationCreateDTO(1L, "study"));
        verify(repository).save(any(Reservation.class));
    }

    @Test void cleanupUsesSeoulClock() {
        ReservationRepository repository = mock(ReservationRepository.class);
        new ReservationClear(repository, clock).cleanReservation();
        verify(repository).deleteAllByCreateAtBefore(LocalDateTime.of(2026, 11, 9, 22, 0));
    }

    @Test void bookingWindowKeepsExclusiveBoundaries() {
        var facade = mock(com.example.server.domain.reservation.facade.ReservationFacade.class);
        for (String time : List.of("08:00:00", "16:20:00")) {
            Clock boundary = Clock.fixed(LocalDateTime.parse("2026-11-09T" + time)
                    .atZone(ZoneId.of(TimeConfig.ZONE)).toInstant(), ZoneId.of(TimeConfig.ZONE));
            new ReservationController(service, facade, null, boundary, slots).RequestCreate("token", null);
        }
        Clock inside = Clock.fixed(Instant.parse("2026-11-09T00:00:00Z"), ZoneId.of(TimeConfig.ZONE));
        CustomException error = assertThrows(CustomException.class,
                () -> new ReservationController(service, facade, null, inside, slots).RequestCreate("token", null));
        assertEquals(ErrorCode.IS_TIME_NOT, error.getCode());
        verify(facade, times(2)).booking("token", null);
    }

    @Test void repeatedAndExistingStudentPeriodsAreConflicts() {
        ReservationRepository reservations = mock(ReservationRepository.class);
        StudentRepository students = mock(StudentRepository.class);
        Student student = Student.builder().build();
        when(reservations.findById(1L)).thenReturn(Optional.of(Reservation.builder().build()));
        when(students.findBySchoolNumber("1234")).thenReturn(Optional.of(student));
        var slotService = new ReservationSlotService(slots, reservations, students);
        CustomException repeated = assertThrows(CustomException.class,
                () -> slotService.createSlot(new CreateSlotDTO("1234", 1L, List.of(1, 1))));
        assertEquals(ErrorCode.NOT_ABLE_RESERVATION, repeated.getCode());
        when(slots.existsByStudentAndPeriodIn(student, List.of(1))).thenReturn(true);
        assertThrows(CustomException.class,
                () -> slotService.createSlot(new CreateSlotDTO("1234", 1L, List.of(1))));
        verify(slots, never()).saveAll(any());
    }

    @Test void databaseRaceReturnsStructuredConflict() {
        var response = new GlobalExceptionHandler().handleIntegrityConflict(new DataIntegrityViolationException("duplicate"));
        assertEquals(409, response.getStatusCode().value());
        assertNotNull(response.getBody());
    }
}
