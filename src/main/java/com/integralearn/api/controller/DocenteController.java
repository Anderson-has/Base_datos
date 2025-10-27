package com.integralearn.api.controller;

import com.integralearn.api.domain.Docente;
import com.integralearn.api.domain.Persona;
import com.integralearn.api.service.DocenteService;
import com.integralearn.api.service.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/docentes")
@CrossOrigin(origins = "*")
public class DocenteController {

    @Autowired
    private DocenteService docenteService;

    @Autowired
    private PersonaService personaService;

    @GetMapping
    public List<Docente> getAllDocentes() {
        return docenteService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Docente> getDocenteById(@PathVariable Integer id) {
        Optional<Docente> docente = docenteService.findById(id);
        return docente.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/cargo/{cargo}")
    public List<Docente> getDocentesByCargo(@PathVariable String cargo) {
        return docenteService.findByCargo(cargo);
    }

    @PostMapping
    public ResponseEntity<Docente> createDocente(@RequestBody DocenteRequest request) {
        Optional<Persona> personaOpt = personaService.findById(request.getPersonaId());
        if (personaOpt.isPresent()) {
            Docente docente = docenteService.createDocente(
                    personaOpt.get(), 
                    request.getCargo()
            );
            return ResponseEntity.ok(docente);
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Docente> updateDocente(@PathVariable Integer id, @RequestBody Docente docenteDetails) {
        Optional<Docente> docenteOpt = docenteService.findById(id);
        if (docenteOpt.isPresent()) {
            Docente docente = docenteOpt.get();
            docente.setCargo(docenteDetails.getCargo());
            return ResponseEntity.ok(docenteService.save(docente));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDocente(@PathVariable Integer id) {
        if (docenteService.findById(id).isPresent()) {
            docenteService.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Inner class for request body
    public static class DocenteRequest {
        private Integer personaId;
        private String cargo;

        public Integer getPersonaId() { return personaId; }
        public void setPersonaId(Integer personaId) { this.personaId = personaId; }
        public String getCargo() { return cargo; }
        public void setCargo(String cargo) { this.cargo = cargo; }
    }
}
