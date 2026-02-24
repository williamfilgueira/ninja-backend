package com.ninja_br.poc.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record CreateUserRequest(
        @NotBlank @Email @Size(max = 120) String email,
        @NotBlank @Size(min = 6, max = 72) String senha,
        @NotNull String role,
        LocalDateTime expiraEm,
        @Size(max = 150) String nome
) {
}
