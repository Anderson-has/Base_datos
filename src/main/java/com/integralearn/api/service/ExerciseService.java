package com.integralearn.api.service;

import com.integralearn.api.domain.Exercise;
import com.integralearn.api.dto.ExerciseDto;
import com.integralearn.api.repo.ExerciseRepository;
import com.integralearn.api.repo.ScenarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ExerciseService {

    @Autowired
    private ExerciseRepository exercises;
    @Autowired
    private ScenarioRepository scenarios;

    public List<ExerciseDto> list(String scenarioCode) {
        List<Exercise> all;
        if (scenarioCode != null && !scenarioCode.isBlank()) {
            var sc = scenarios.findByCode(scenarioCode).orElseThrow();
            all = exercises.findByScenarioId(sc.getId());
        } else {
            all = exercises.findAll();
        }
        return all.stream()
                .map(e -> new ExerciseDto(
                e.getId(), e.getCode(), e.getTitle(), e.getMaxScore(),
                e.getTimeLimitSeconds(), e.getScenario().getCode()))
                .toList();
    }

    public ExerciseDto getOne(Long id) {
        var e = exercises.findById(id).orElseThrow();
        return new ExerciseDto(
                e.getId(), e.getCode(), e.getTitle(), e.getMaxScore(),
                e.getTimeLimitSeconds(), e.getScenario().getCode());
    }
}
