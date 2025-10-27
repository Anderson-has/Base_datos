package com.integralearn.api.controller;

import com.integralearn.api.dto.ScenarioDto;
import com.integralearn.api.service.ScenarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/scenarios")
public class ScenarioController {

    @Autowired
    private ScenarioService service;

    @GetMapping
    public List<ScenarioDto> list(@RequestParam(required = false) Boolean enabled) {
        return service.list(enabled);
    }

    @GetMapping("/{code}")
    public ScenarioDto get(@PathVariable String code) {
        return service.getByCode(code);
    }
}
