package com.integralearn.api.repo;

import com.integralearn.api.domain.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante, Integer> {
    
    List<Estudiante> findBySemestre(Integer semestre);
    
    List<Estudiante> findByProgresoGreaterThan(BigDecimal progreso);
}
