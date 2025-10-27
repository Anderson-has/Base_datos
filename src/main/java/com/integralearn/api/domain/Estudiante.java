package com.integralearn.api.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "Estudiante")
public class Estudiante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "persona_id", nullable = false)
    private Persona persona;

    @Column(name = "semestre", nullable = false)
    private Integer semestre;

    @Column(name = "progreso", nullable = false, columnDefinition = "DECIMAL(5,2)")
    private BigDecimal progreso;

    public Estudiante() {
    }

    public Estudiante(Persona persona, Integer semestre, BigDecimal progreso) {
        this.persona = persona;
        this.semestre = semestre;
        this.progreso = progreso;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public Integer getSemestre() {
        return semestre;
    }

    public void setSemestre(Integer semestre) {
        this.semestre = semestre;
    }

    public BigDecimal getProgreso() {
        return progreso;
    }

    public void setProgreso(BigDecimal progreso) {
        this.progreso = progreso;
    }

    @Override
    public String toString() {
        return "Estudiante{" +
                "id=" + id +
                ", semestre=" + semestre +
                ", progreso=" + progreso +
                '}';
    }
}
