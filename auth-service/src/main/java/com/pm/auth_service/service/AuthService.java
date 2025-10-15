package com.pm.auth_service.service;

import com.pm.auth_service.dto.LoginRequestDto;
import com.pm.auth_service.model.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UserService userService;

    public AuthService(UserService userService) {
        this.userService = userService;
    }

    public Optional<String> authenticate(LoginRequestDto loginRequestDto)
    {
        Optional<String> token = userService.findByEmail(loginRequestDto.getEmail())
                .filter(u-> passwordEncoder.matches(loginRequestDto.getPassword(), u.getPassword()))
                .map(u-> jwtUtill.generateToken(u.getEmail(), u.getRole()));
        return token;
    }

}
