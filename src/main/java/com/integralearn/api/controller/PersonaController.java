package com.integralearn.api.controller;

import com.integralearn.api.domain.Persona;
import com.integralearn.api.service.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/personas")
@CrossOrigin(origins = "*")
public class PersonaController {

    @Autowired
    private PersonaService personaService;

    @GetMapping
    public List<Persona> getAllPersonas() {
        return personaService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Persona> getPersonaById(@PathVariable Integer id) {
        Optional<Persona> persona = personaService.findById(id);
        return persona.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/gmail/{gmail}")
    public ResponseEntity<Persona> getPersonaByGmail(@PathVariable String gmail) {
        Optional<Persona> persona = personaService.findByGmail(gmail);
        return persona.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Persona createPersona(@RequestBody Persona persona) {
        return personaService.save(persona);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Persona> updatePersona(@PathVariable Integer id, @RequestBody Persona personaDetails) {
        Optional<Persona> personaOpt = personaService.findById(id);
        if (personaOpt.isPresent()) {
            Persona persona = personaOpt.get();
            persona.setGmail(personaDetails.getGmail());
            persona.setNombre(personaDetails.getNombre());
            persona.setApellido(personaDetails.getApellido());
            persona.setContrasenia(personaDetails.getContrasenia());
            return ResponseEntity.ok(personaService.save(persona));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePersona(@PathVariable Integer id) {
        if (personaService.findById(id).isPresent()) {
            personaService.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
