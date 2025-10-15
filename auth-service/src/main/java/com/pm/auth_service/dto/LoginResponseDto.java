package com.pm.auth_service.dto;

import lombok.Data;

@Data
public class LoginResponseDto {
    private final String token;

    public LoginResponseDto(String token) {
        this.token = token;
    }
}
