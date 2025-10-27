package com.integralearn.api.dto;

import jakarta.validation.constraints.*;

public record LoginDto(
        @NotBlank
        String usernameOrEmail,
        @NotBlank
        String password
        ) {

}
