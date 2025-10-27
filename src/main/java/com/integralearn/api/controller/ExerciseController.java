package com.integralearn.api.controller;

import com.integralearn.api.dto.ExerciseDto;
import com.integralearn.api.service.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exercises")
public class ExerciseController {

    @Autowired
    private ExerciseService service;

    @GetMapping
    public List<ExerciseDto> list(@RequestParam(required = false, name = "scenario") String scenarioCode) {
        return service.list(scenarioCode);
    }

    @GetMapping("/{id}")
    public ExerciseDto getOne(@PathVariable Long id) {
        return service.getOne(id);
    }
}
