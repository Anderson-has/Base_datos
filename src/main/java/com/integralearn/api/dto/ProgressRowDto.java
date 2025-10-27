package com.integralearn.api.dto;

import java.time.LocalDateTime;

public record ProgressRowDto(
        Long userId,
        String username,
        Long scenarioId,
        String scenarioCode,
        Integer bestScore,
        Integer totalScore,
        Integer attemptsCount,
        Integer totalTimeSeconds,
        LocalDateTime lastActivityAt
        ) {

}
