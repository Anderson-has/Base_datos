package com.integralearn.api.domain;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "persona_id")
    private Persona persona;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "email")
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserType type;

    private boolean active = true;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    // Convenience methods to access Persona data
    public String getPersonaGmail() {
        return persona != null ? persona.getGmail() : null;
    }

    public String getPersonaPasswordHash() {
        return persona != null ? persona.getContrasenia() : null;
    }

    public String getFirstName() {
        return persona != null ? persona.getNombre() : null;
    }

    public String getLastName() {
        return persona != null ? persona.getApellido() : null;
    }

    public Integer getSemester() {
        if (persona != null && persona.getEstudiante() != null) {
            return persona.getEstudiante().getSemestre();
        }
        return null;
    }
}
