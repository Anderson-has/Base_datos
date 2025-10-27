package com.integralearn.api.service;

import com.integralearn.api.domain.*;
import com.integralearn.api.dto.*;
import com.integralearn.api.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class AttemptsService {

    @Autowired
    private UserRepository users;
    @Autowired
    private ExerciseRepository exercises;
    @Autowired
    private ExerciseAttemptRepository attempts;

    public AttemptDto registerAttempt(Long exerciseId, String username, AttemptStartEndDto dto) {
        var user = users.findByPersona_Gmail(username).orElseThrow();
        var ex = exercises.findById(exerciseId).orElseThrow();
        int duration = (int) Duration.between(dto.startedAt(), dto.endedAt()).getSeconds();
        if (duration < 0) {
            duration = 0;
        }

        var a = new ExerciseAttempt();
        a.setUser(user);
        a.setExercise(ex);
        a.setStartedAt(dto.startedAt());
        a.setEndedAt(dto.endedAt());
        a.setDurationSeconds(duration);
        a.setScoreObtained(dto.scoreObtained());
        a.setPassed(Boolean.TRUE.equals(dto.passed()));
        a.setMetadataJson(dto.metadataJson());

        var saved = attempts.save(a);
        return new AttemptDto(saved.getId(), ex.getId(), saved.getStartedAt(), saved.getEndedAt(),
                saved.getDurationSeconds(), saved.getScoreObtained(), saved.isPassed());
    }
}
