package com.integralearn.api.controller;

import com.integralearn.api.domain.Estudiante;
import com.integralearn.api.domain.Persona;
import com.integralearn.api.service.EstudianteService;
import com.integralearn.api.service.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/estudiantes")
@CrossOrigin(origins = "*")
public class EstudianteController {

    @Autowired
    private EstudianteService estudianteService;

    @Autowired
    private PersonaService personaService;

    @GetMapping
    public List<Estudiante> getAllEstudiantes() {
        return estudianteService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Estudiante> getEstudianteById(@PathVariable Integer id) {
        Optional<Estudiante> estudiante = estudianteService.findById(id);
        return estudiante.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/semestre/{semestre}")
    public List<Estudiante> getEstudiantesBySemestre(@PathVariable Integer semestre) {
        return estudianteService.findBySemestre(semestre);
    }

    @GetMapping("/progreso/{progreso}")
    public List<Estudiante> getEstudiantesByProgreso(@PathVariable BigDecimal progreso) {
        return estudianteService.findByProgresoGreaterThan(progreso);
    }

    @PostMapping
    public ResponseEntity<Estudiante> createEstudiante(@RequestBody EstudianteRequest request) {
        Optional<Persona> personaOpt = personaService.findById(request.getPersonaId());
        if (personaOpt.isPresent()) {
            Estudiante estudiante = estudianteService.createEstudiante(
                    personaOpt.get(), 
                    request.getSemestre(), 
                    request.getProgreso()
            );
            return ResponseEntity.ok(estudiante);
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Estudiante> updateEstudiante(@PathVariable Integer id, @RequestBody Estudiante estudianteDetails) {
        Optional<Estudiante> estudianteOpt = estudianteService.findById(id);
        if (estudianteOpt.isPresent()) {
            Estudiante estudiante = estudianteOpt.get();
            estudiante.setSemestre(estudianteDetails.getSemestre());
            estudiante.setProgreso(estudianteDetails.getProgreso());
            return ResponseEntity.ok(estudianteService.save(estudiante));
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}/progreso")
    public ResponseEntity<Estudiante> updateProgreso(@PathVariable Integer id, @RequestBody BigDecimal progreso) {
        Estudiante estudiante = estudianteService.updateProgreso(id, progreso);
        if (estudiante != null) {
            return ResponseEntity.ok(estudiante);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEstudiante(@PathVariable Integer id) {
        if (estudianteService.findById(id).isPresent()) {
            estudianteService.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Inner class for request body
    public static class EstudianteRequest {
        private Integer personaId;
        private Integer semestre;
        private BigDecimal progreso;

        public Integer getPersonaId() { return personaId; }
        public void setPersonaId(Integer personaId) { this.personaId = personaId; }
        public Integer getSemestre() { return semestre; }
        public void setSemestre(Integer semestre) { this.semestre = semestre; }
        public BigDecimal getProgreso() { return progreso; }
        public void setProgreso(BigDecimal progreso) { this.progreso = progreso; }
    }
}
