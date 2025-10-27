package com.integralearn.api.dto;

public record TokenResponse(String token, String tokenType) {

    public static TokenResponse bearer(String t) {
        return new TokenResponse(t, "Bearer");
    }
}
