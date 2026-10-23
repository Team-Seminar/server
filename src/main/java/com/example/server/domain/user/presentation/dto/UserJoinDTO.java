package com.example.server.domain.user.presentation.dto;

//유저 회원가입 dto
public record UserJoinDTO(
      String loginId,
      String pw,
      String checkPw
) {
}
