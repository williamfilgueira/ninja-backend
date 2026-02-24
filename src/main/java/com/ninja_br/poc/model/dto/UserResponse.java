package com.ninja_br.poc.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

import java.time.Instant;
import java.time.LocalDateTime;

public record UserResponse(
        String id,
        String nome,
        String email,
        String role,
        boolean bloqueado,
        LocalDateTime expiraEm,
        Instant dtCriacao,
        Instant dtAtz
) {
}