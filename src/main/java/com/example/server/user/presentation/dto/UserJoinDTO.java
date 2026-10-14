package com.example.server.user.presentation.dto;

public record UserJoinDTO(
      String loginId,
      String pw,
      String checkPw
) {
}
