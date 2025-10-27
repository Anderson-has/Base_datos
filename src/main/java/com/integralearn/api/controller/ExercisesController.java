package com.integralearn.api.controller;

import com.integralearn.api.dto.*;
import com.integralearn.api.service.AttemptsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/exercises")
public class ExercisesController {

    @Autowired
    private AttemptsService attempts;

    @PostMapping("/{exerciseId}/attempts")
    public AttemptDto registerAttempt(@PathVariable Long exerciseId,
            @Valid @RequestBody AttemptStartEndDto dto,
            Authentication auth) {
        return attempts.registerAttempt(exerciseId, auth.getName(), dto);
    }
}
