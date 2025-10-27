package com.integralearn.api.dto;

import java.time.LocalDateTime;

public record AttemptDto(
        Long id, Long exerciseId, LocalDateTime startedAt, LocalDateTime endedAt,
        Integer durationSeconds, Integer scoreObtained, Boolean passed
        ) {

}
