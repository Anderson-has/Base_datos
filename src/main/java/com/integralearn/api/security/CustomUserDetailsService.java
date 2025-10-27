package com.integralearn.api.security;

import com.integralearn.api.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository users;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        System.out.println("=== DEBUG: loadUserByUsername ===");
        System.out.println("Buscando: " + usernameOrEmail);
        
        var userOpt1 = users.findByUsername(usernameOrEmail);
        System.out.println("Usuario por username: " + (userOpt1.isPresent() ? "Encontrado ID " + userOpt1.get().getId() : "No encontrado"));
        
        var userOpt2 = users.findByPersona_Gmail(usernameOrEmail);
        System.out.println("Usuario por email: " + (userOpt2.isPresent() ? "Encontrado ID " + userOpt2.get().getId() : "No encontrado"));
        
        var user = userOpt1
                .or(() -> userOpt2)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        
        System.out.println("Usuario encontrado: ID=" + user.getId() + ", username=" + user.getUsername());
        System.out.println("Password hash: " + user.getPersonaPasswordHash());

        var authorities = user.getRoles().stream()
                .map(r -> new SimpleGrantedAuthority(r.getName()))
                .collect(Collectors.toSet());

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPersonaPasswordHash(), user.isActive(),
                true, true, true, authorities);
    }
}
