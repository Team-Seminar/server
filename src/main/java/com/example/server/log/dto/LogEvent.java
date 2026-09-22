package com.example.server.log.dto;

import com.example.server.log.LogStatus;

public record LogEvent(
        String content,
        LogStatus status
) {
}
