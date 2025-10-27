package com.integralearn.api.service;

import com.integralearn.api.domain.Scenario;
import com.integralearn.api.dto.ScenarioDto;
import com.integralearn.api.repo.ScenarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ScenarioService {

    @Autowired
    private ScenarioRepository scenarios;

    public List<ScenarioDto> list(Boolean enabled) {
        List<Scenario> all = (enabled == null) ? scenarios.findAll() : scenarios.findByEnabled(enabled);
        return all.stream().map(s -> new ScenarioDto(s.getId(), s.getCode(), s.getTitle(), s.isEnabled())).toList();
    }

    public ScenarioDto getByCode(String code) {
        var s = scenarios.findByCode(code).orElseThrow();
        return new ScenarioDto(s.getId(), s.getCode(), s.getTitle(), s.isEnabled());
    }
}
