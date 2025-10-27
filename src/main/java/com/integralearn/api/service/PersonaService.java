package com.integralearn.api.service;

import com.integralearn.api.domain.Persona;
import com.integralearn.api.repo.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PersonaService {

    @Autowired
    private PersonaRepository personaRepository;

    public List<Persona> findAll() {
        return personaRepository.findAll();
    }

    public Optional<Persona> findById(Integer id) {
        return personaRepository.findById(id);
    }

    public Optional<Persona> findByGmail(String gmail) {
        return personaRepository.findByGmail(gmail);
    }

    public Persona save(Persona persona) {
        return personaRepository.save(persona);
    }

    public void deleteById(Integer id) {
        personaRepository.deleteById(id);
    }

    public boolean existsByGmail(String gmail) {
        return personaRepository.existsByGmail(gmail);
    }
}
