package com.example.server.domain.user.presentation.dto;

public record UserJoinDTO(
      String loginId,
      String pw,
      String checkPw
) {
}
