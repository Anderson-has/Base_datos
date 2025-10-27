package com.integralearn.api.dto;

import java.util.Set;

public record UserDto(
        Long id, String username, String email, boolean active, Set<String> roles
        ) {

}
