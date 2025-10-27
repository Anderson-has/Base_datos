package com.integralearn.api.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "Persona")
public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "gmail", unique = true, nullable = false, length = 100)
    private String gmail;

    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;

    @Column(name = "apellido", nullable = false, length = 50)
    private String apellido;

    @Column(name = "contrasenia", nullable = false, length = 100)
    private String contrasenia;

    @OneToOne(mappedBy = "persona", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Estudiante estudiante;

    @OneToOne(mappedBy = "persona", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Docente docente;

    public Persona() {
    }

    public Persona(String gmail, String nombre, String apellido, String contrasenia) {
        this.gmail = gmail;
        this.nombre = nombre;
        this.apellido = apellido;
        this.contrasenia = contrasenia;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public Docente getDocente() {
        return docente;
    }

    public void setDocente(Docente docente) {
        this.docente = docente;
    }

    @Override
    public String toString() {
        return "Persona{" +
                "id=" + id +
                ", gmail='" + gmail + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                '}';
    }
}
