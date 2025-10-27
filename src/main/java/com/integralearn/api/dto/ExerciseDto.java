package com.integralearn.api.dto;

public record ExerciseDto(Long id, String code, String title, Integer maxScore, Integer timeLimitSeconds, String scenarioCode) {

}
