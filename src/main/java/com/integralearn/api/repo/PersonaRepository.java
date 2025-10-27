package com.integralearn.api.repo;

import com.integralearn.api.domain.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Integer> {
    
    Optional<Persona> findByGmail(String gmail);
    
    boolean existsByGmail(String gmail);
}
