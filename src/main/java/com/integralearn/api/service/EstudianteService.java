package com.integralearn.api.service;

import com.integralearn.api.domain.Estudiante;
import com.integralearn.api.domain.Persona;
import com.integralearn.api.repo.EstudianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EstudianteService {

    @Autowired
    private EstudianteRepository estudianteRepository;

    public List<Estudiante> findAll() {
        return estudianteRepository.findAll();
    }

    public Optional<Estudiante> findById(Integer id) {
        return estudianteRepository.findById(id);
    }

    public List<Estudiante> findBySemestre(Integer semestre) {
        return estudianteRepository.findBySemestre(semestre);
    }

    public List<Estudiante> findByProgresoGreaterThan(BigDecimal progreso) {
        return estudianteRepository.findByProgresoGreaterThan(progreso);
    }

    public Estudiante save(Estudiante estudiante) {
        return estudianteRepository.save(estudiante);
    }

    public Estudiante createEstudiante(Persona persona, Integer semestre, BigDecimal progreso) {
        Estudiante estudiante = new Estudiante(persona, semestre, progreso);
        return estudianteRepository.save(estudiante);
    }

    public void deleteById(Integer id) {
        estudianteRepository.deleteById(id);
    }

    public Estudiante updateProgreso(Integer id, BigDecimal nuevoProgreso) {
        Optional<Estudiante> estudianteOpt = estudianteRepository.findById(id);
        if (estudianteOpt.isPresent()) {
            Estudiante estudiante = estudianteOpt.get();
            estudiante.setProgreso(nuevoProgreso);
            return estudianteRepository.save(estudiante);
        }
        return null;
    }
}
