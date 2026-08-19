package com.example.server.user;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties(prefix = "user")
public class UserProperties {
    private String teacherPw;
}
