package com.example.authenticationservice.dto;

import lombok.Builder;

@Builder
public record TokenResponse( String accessToken,  String refreshToken) {
}
