package com.integralearn.api.controller;

import com.integralearn.api.dto.*;
import com.integralearn.api.service.AuthService;
import com.integralearn.api.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService auth;

    @Autowired
    private UserService userService; // <-- inyecta el servicio

    @GetMapping("/me")
    public ResponseEntity<UserDto> me(Authentication authentication) {
        String username = authentication.getName();        // viene del token
        UserDto dto = userService.me(username);            // usa tu método existente del servicio
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/register")
    @CrossOrigin(origins = "http://localhost:3000")
    public UserDto register(@Valid @RequestBody RegisterDto dto) {
        System.out.println("=== RECIBIENDO REGISTRO ===");
        System.out.println("Email: " + dto.email());
        System.out.println("First Name: " + dto.firstName());
        System.out.println("Last Name: " + dto.lastName());
        System.out.println("Type: " + dto.type());
        try {
            UserDto result = auth.register(dto);
            System.out.println("=== REGISTRO EXITOSO ===");
            return result;
        } catch (Exception e) {
            System.err.println("=== ERROR EN REGISTRO ===");
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
    
    // Endpoint de prueba para verificar que el controlador funciona
    @GetMapping("/test")
    @CrossOrigin(origins = "http://localhost:3000")
    public String test() {
        System.out.println("=== TEST ENDPOINT HIT ===");
        return "OK";
    }

    @PostMapping("/login")
    public TokenResponse login(@Valid @RequestBody LoginDto dto) {
        return auth.login(dto);
    }
}
