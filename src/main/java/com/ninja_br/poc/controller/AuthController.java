package com.ninja_br.poc.controller;

import com.ninja_br.poc.service.AuthService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthService.LoginResult> login(@RequestBody @Valid LoginRequest req) {
        return ResponseEntity.ok(authService.login(req.getEmail(), req.getSenha()));
    }

    @GetMapping("/me")
    public ResponseEntity<MeResponse> me(@AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok(new MeResponse(
                jwt.getSubject(),
                jwt.getClaimAsString("email"),
                jwt.getClaimAsString("role")
        ));
    }

    public static class LoginRequest {
        @Email
        @NotBlank
        private String email;
        @NotBlank
        private String senha;

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getSenha() { return senha; }
        public void setSenha(String senha) { this.senha = senha; }
    }

    public static class MeResponse {
        private String userId;
        private String email;
        private String role;

        public MeResponse(String userId, String email, String role) {
            this.userId = userId;
            this.email = email;
            this.role = role;
        }

        public String getUserId() { return userId; }
        public String getEmail() { return email; }
        public String getRole() { return role; }
    }
}