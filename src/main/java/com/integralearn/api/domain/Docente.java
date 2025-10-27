package com.integralearn.api.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "Docente")
public class Docente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "persona_id", nullable = false)
    private Persona persona;

    @Column(name = "cargo", nullable = false, length = 100)
    private String cargo;

    public Docente() {
    }

    public Docente(Persona persona, String cargo) {
        this.persona = persona;
        this.cargo = cargo;
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

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    @Override
    public String toString() {
        return "Docente{" +
                "id=" + id +
                ", cargo='" + cargo + '\'' +
                '}';
    }
}
