package com.example.server.DTO;

import lombok.Getter;

public record StudentJoinDTO (
        String schoolNumber,
        String loginId,
        String pw,
        String checkPw
){
}
