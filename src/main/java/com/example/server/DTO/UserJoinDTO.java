package com.example.server.DTO;

import lombok.Getter;

@Getter
public class UserJoinDTO {
    String name;
    String pw;
    String checkPw;
    String teacherPw="";
}
