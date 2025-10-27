package com.integralearn.api.service;

import com.integralearn.api.domain.Docente;
import com.integralearn.api.domain.Persona;
import com.integralearn.api.repo.DocenteRepository;
import com.integralearn.api.repo.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DocenteService {

    @Autowired
    private DocenteRepository docenteRepository;

    public List<Docente> findAll() {
        return docenteRepository.findAll();
    }

    public Optional<Docente> findById(Integer id) {
        return docenteRepository.findById(id);
    }

    public List<Docente> findByCargo(String cargo) {
        return docenteRepository.findByCargo(cargo);
    }

    public Docente save(Docente docente) {
        return docenteRepository.save(docente);
    }

    public Docente createDocente(Persona persona, String cargo) {
        Docente docente = new Docente(persona, cargo);
        return docenteRepository.save(docente);
    }

    public void deleteById(Integer id) {
        docenteRepository.deleteById(id);
    }
}
