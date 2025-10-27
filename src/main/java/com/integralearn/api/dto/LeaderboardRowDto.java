package com.integralearn.api.dto;

public record LeaderboardRowDto(
        Long scenarioId,
        String scenarioCode,
        Long userId,
        String username,
        Integer bestScore
        ) {

}
