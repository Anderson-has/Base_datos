package com.integralearn.api.config;

import com.integralearn.api.domain.Role;
import com.integralearn.api.repo.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class RoleInitializer implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) {
        System.out.println("=== INICIALIZANDO ROLES ===");
        
        // Verificar y crear ROLE_ESTUDIANTE
        if (!roleRepository.findByName("ROLE_ESTUDIANTE").isPresent()) {
            Role roleEstudiante = new Role();
            roleEstudiante.setName("ROLE_ESTUDIANTE");
            roleRepository.save(roleEstudiante);
            System.out.println("✅ Creado ROLE_ESTUDIANTE");
        } else {
            System.out.println("✓ ROLE_ESTUDIANTE ya existe");
        }

        // Verificar y crear ROLE_DOCENTE
        if (!roleRepository.findByName("ROLE_DOCENTE").isPresent()) {
            Role roleDocente = new Role();
            roleDocente.setName("ROLE_DOCENTE");
            roleRepository.save(roleDocente);
            System.out.println("✅ Creado ROLE_DOCENTE");
        } else {
            System.out.println("✓ ROLE_DOCENTE ya existe");
        }

        System.out.println("=== ROLES INICIALIZADOS ===");
    }
}
