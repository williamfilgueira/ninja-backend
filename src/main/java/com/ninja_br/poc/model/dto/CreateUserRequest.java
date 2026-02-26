package com.ninja_br.poc.model.dto;

import com.ninja_br.poc.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.Instant;
import java.time.LocalDateTime;

public record CreateUserRequest(
        @NotNull String nome,
        @NotNull @Email String email,
        @NotNull String senha,
        @NotNull Role role,
        boolean bloqueado,
        Instant expiraEm
) {
}
