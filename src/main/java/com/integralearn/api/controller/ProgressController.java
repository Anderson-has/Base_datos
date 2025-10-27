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
        return progress.progressForUser(auth.getName());
    }

    @GetMapping("/leaderboard")
    public List<LeaderboardRowDto> leaderboard(@RequestParam("scenario") String scenarioCode) {
        return progress.leaderboard(scenarioCode);
    }
}
