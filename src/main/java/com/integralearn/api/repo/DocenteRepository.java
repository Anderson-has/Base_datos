package com.integralearn.api.repo;

import com.integralearn.api.domain.Docente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocenteRepository extends JpaRepository<Docente, Integer> {
    
    List<Docente> findByCargo(String cargo);
}
