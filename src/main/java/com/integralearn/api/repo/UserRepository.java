// com.integralearn.api.repo.UserRepository.java
package com.integralearn.api.repo;

import com.integralearn.api.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
    
    Optional<User> findByPersona_Gmail(String gmail);

    // Estudiantes por rol (ROLE_ESTUDIANTE)
    @Query("""
        select distinct u
        from User u
        join u.roles r
        where r.name = 'ROLE_ESTUDIANTE'
    """)
    List<User> findAllStudents();

    // Estudiantes por activo/inactivo
    @Query("""
        select distinct u
        from User u
        join u.roles r
        where r.name = 'ROLE_ESTUDIANTE'
          and u.active = :active
    """)
    List<User> findAllStudentsByActive(@Param("active") boolean active);
}
