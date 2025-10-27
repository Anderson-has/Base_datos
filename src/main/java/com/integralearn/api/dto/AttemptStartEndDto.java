package com.integralearn.api.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

public record AttemptStartEndDto(
        @NotNull
        LocalDateTime startedAt,
        @NotNull
        LocalDateTime endedAt,
        @NotNull
        Integer scoreObtained,
        @NotNull
        Boolean passed,
        String metadataJson
        ) {

}
