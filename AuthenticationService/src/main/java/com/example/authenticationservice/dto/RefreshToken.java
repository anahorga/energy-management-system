package com.example.authenticationservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record RefreshToken(@NotBlank String refreshToken) {
}
