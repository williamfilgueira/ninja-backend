package com.ninja_br.poc.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record UpdateUserRequest(
        @Size(max = 150) String nome,
        @Email @Size(max = 120) String email,
        @Size(min = 6, max = 72) String senha,
        String role,
        LocalDateTime expiraEm,
        Boolean bloqueado
) {
}