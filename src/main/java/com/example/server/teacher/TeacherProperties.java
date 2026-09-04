package com.example.server.teacher;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties(prefix = "user")
public class TeacherProperties {
    private String teacherPw;
}
