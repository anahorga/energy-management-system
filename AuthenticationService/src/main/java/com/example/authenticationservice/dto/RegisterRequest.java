package com.example.authenticationservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record RegisterRequest(@NotBlank String username, @NotBlank String password) {
}
