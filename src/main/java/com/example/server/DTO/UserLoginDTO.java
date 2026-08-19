package com.example.server.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserLoginDTO {
    String name;
    String pw;
}
