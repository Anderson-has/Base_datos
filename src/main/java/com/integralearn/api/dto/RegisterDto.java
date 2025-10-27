package com.integralearn.api.dto;

import jakarta.validation.constraints.*;
import com.integralearn.api.domain.UserType;

public record RegisterDto(
        @NotBlank
        @Email
        String email,
        @NotBlank
        String password,
        String firstName,
        String lastName,
        @NotNull
        UserType type,
        Integer semester
        ) {

}
