package com.integralearn.api.controller;

import com.integralearn.api.dto.LeaderboardRowDto;
import com.integralearn.api.dto.ProgressRowDto;
import com.integralearn.api.service.ProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/progress")
public class ProgressController {

    @Autowired
    private ProgressService progress;

    @GetMapping("/me")
    public List<ProgressRowDto> myProgress(Authentication auth) {
        // Temporal: usar usuario específico para pruebas
        String username = (auth != null && auth.getName() != null) ? auth.getName() : "alex@gmail.com";
        return progress.progressForUser(username);
    }

    @GetMapping("/leaderboard")
    public List<LeaderboardRowDto> leaderboard(@RequestParam("scenario") String scenarioCode) {
        return progress.leaderboard(scenarioCode);
    }

    @GetMapping("/test")
    public String test() {
        return "Progress controller is working!";
    }
}
