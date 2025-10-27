// com.integralearn.api.controller.UserController.java
package com.integralearn.api.controller;

import com.integralearn.api.dto.StatusDto;
import com.integralearn.api.dto.UserDto;
import com.integralearn.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin // opcional si vas a llamar desde otro dominio
public class UserController {

    @Autowired
    private UserService users;

    @GetMapping("/me")
    public UserDto me(Authentication auth) {
        return users.me(auth.getName());
    }

    // NUEVO: listar estudiantes (opcionalmente ?active=true/false)
    @PreAuthorize("hasAnyRole('DOCENTE','ADMIN')")
    @GetMapping
    public List<UserDto> listStudents(@RequestParam(name = "active", required = false) Boolean active) {
        return users.listStudents(active);
    }

    @PreAuthorize("hasAnyRole('DOCENTE','ADMIN')")
    @PatchMapping("/{id}/status")
    public UserDto setActive(@PathVariable("id") Long id, // ?nombre explícito
            @RequestBody StatusDto dto) {   // body con { "active": true|false }
        return users.setActive(id, dto.active());
    }
}
