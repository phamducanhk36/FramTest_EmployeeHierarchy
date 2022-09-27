package com.example.employeehierarchy.controllers;

import com.example.employeehierarchy.dtos.LoginRequest;
import com.example.employeehierarchy.dtos.LoginResponse;
import com.example.employeehierarchy.jwt.JwtTokenUtil;
import com.example.employeehierarchy.models.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final AuthenticationManager authManager;
    private final JwtTokenUtil jwtUtil;

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            User user = (User) authentication.getPrincipal();
            String accessToken = jwtUtil.generateAccessToken(user);
            log.debug("[/auth/login] User Logged in: {}", request.getUsername());
            return ResponseEntity.ok().body(new LoginResponse(accessToken));

        } catch (BadCredentialsException ex) {
            log.error("[/auth/login] BadCredentialsException: ", ex);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
