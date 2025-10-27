// com.integralearn.api.service.UserService.java
package com.integralearn.api.service;

import com.integralearn.api.domain.User;
import com.integralearn.api.dto.UserDto;
import com.integralearn.api.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository users;

    public UserDto me(String username) {
        User u = users.findByUsername(username)
                .or(() -> users.findByPersona_Gmail(username))
                .orElseThrow();
        return toDto(u);
    }

    public Optional<User> findByGmail(String gmail) {
        return users.findByPersona_Gmail(gmail);
    }

    public List<UserDto> listStudents(Boolean active) {
        List<User> list = (active == null)
                ? users.findAllStudents()
                : users.findAllStudentsByActive(active.booleanValue());
        return list.stream().map(this::toDto).collect(Collectors.toList());
    }

    public UserDto setActive(Long id, boolean active) {
        User u = users.findById(id).orElseThrow();
        u.setActive(active);
        users.save(u);
        return toDto(u);
    }

    private UserDto toDto(User u) {
        return new UserDto(
                u.getId(),
                u.getUsername(),
                u.getEmail(),
                u.isActive(),
                u.getRoles().stream().map(r -> r.getName()).collect(Collectors.toSet())
        );
    }
}
