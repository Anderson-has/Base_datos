package com.integralearn.api.service;

import com.integralearn.api.domain.*;
import com.integralearn.api.dto.*;
import com.integralearn.api.repo.*;
import com.integralearn.api.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.stream.Collectors;

@Service
@Transactional
public class AuthService {

    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private UserRepository users;
    @Autowired
    private PersonaRepository personaRepository;
    @Autowired
    private EstudianteRepository estudianteRepository;
    @Autowired
    private DocenteRepository docenteRepository;
    @Autowired
    private RoleRepository roles;
    @Autowired
    private JwtService jwt;

    public UserDto register(RegisterDto dto) {
        System.out.println("=== DEBUG: Iniciando registro de usuario ===");
        System.out.println("Email: " + dto.email());
        System.out.println("Type: " + dto.type());
        System.out.println("First Name: " + dto.firstName());
        System.out.println("Last Name: " + dto.lastName());
        
        if (personaRepository.existsByGmail(dto.email())) {
            throw new IllegalArgumentException("email en uso");
        }

        // Create Persona
        Persona persona = new Persona();
        persona.setGmail(dto.email());
        persona.setNombre(dto.firstName() != null ? dto.firstName() : "");
        persona.setApellido(dto.lastName() != null ? dto.lastName() : "");
        persona.setContrasenia(encoder.encode(dto.password()));
        Persona savedPersona = personaRepository.saveAndFlush(persona);
        System.out.println("DEBUG: Persona guardada con ID: " + savedPersona.getId());

        // Create specific role entity
        if (dto.type() == UserType.ESTUDIANTE) {
            Estudiante estudiante = new Estudiante();
            estudiante.setPersona(savedPersona);
            estudiante.setSemestre(dto.semester() != null ? dto.semester() : 1);
            estudiante.setProgreso(BigDecimal.ZERO);
            // Establish bidirectional relationship
            savedPersona.setEstudiante(estudiante);
            Estudiante savedEstudiante = estudianteRepository.saveAndFlush(estudiante);
            System.out.println("DEBUG: Estudiante guardado con ID: " + savedEstudiante.getId());
        } else if (dto.type() == UserType.DOCENTE) {
            Docente docente = new Docente();
            docente.setPersona(savedPersona);
            docente.setCargo("Docente"); // Default cargo
            // Establish bidirectional relationship
            savedPersona.setDocente(docente);
            Docente savedDocente = docenteRepository.saveAndFlush(docente);
            System.out.println("DEBUG: Docente guardado con ID: " + savedDocente.getId());
        }

        // Create User wrapper
        User user = new User();
        user.setPersona(savedPersona);
        user.setUsername(dto.email()); // Usar email como username
        user.setEmail(dto.email()); // Establecer email
        user.setType(dto.type());
        user.setActive(true);

        Role base = roles.findByName(dto.type() == UserType.DOCENTE ? "ROLE_DOCENTE" : "ROLE_ESTUDIANTE")
                .orElseThrow(() -> new IllegalStateException("Roles no inicializados"));
        user.getRoles().add(base);

        User saved = users.saveAndFlush(user);
        System.out.println("DEBUG: User guardado con ID: " + saved.getId());
        System.out.println("=== DEBUG: Registro completado exitosamente ===");
        
        return new UserDto(
                saved.getId(), saved.getUsername(), saved.getEmail(),
                saved.isActive(),
                saved.getRoles().stream().map(Role::getName).collect(Collectors.toSet())
        );
    }

    public TokenResponse login(LoginDto dto) {
        System.out.println("=== DEBUG: Intento de login ===");
        System.out.println("Username/Email: " + dto.usernameOrEmail());
        
        try {
            var u1 = users.findByUsername(dto.usernameOrEmail());
            var u2 = users.findByPersona_Gmail(dto.usernameOrEmail());
            System.out.println("Usuario por username: " + (u1.isPresent() ? "Encontrado" : "No encontrado"));
            System.out.println("Usuario por email: " + (u2.isPresent() ? "Encontrado" : "No encontrado"));
            
            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(dto.usernameOrEmail(), dto.password()));
            
            var u = users.findByUsername(dto.usernameOrEmail())
                    .or(() -> users.findByPersona_Gmail(dto.usernameOrEmail()))
                    .orElseThrow();
            if (!u.isActive()) {
                throw new BadCredentialsException("Usuario inhabilitado");
            }
            var token = jwt.generate(u.getUsername(), u.getRoles().stream().map(Role::getName).collect(Collectors.toSet()));
            System.out.println("=== DEBUG: Login exitoso ===");
            return TokenResponse.bearer(token);
        } catch (Exception e) {
            System.err.println("=== DEBUG: Error en login ===");
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
}
