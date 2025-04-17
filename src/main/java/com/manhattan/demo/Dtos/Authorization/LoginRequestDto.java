package com.manhattan.demo.Dtos.Authorization;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequestDto(
        @Email
        @NotBlank(message = "O campo email é obrigatório")
        String email,
        @NotBlank(message = "O campo senha é obrigatório")
        String senha
) {
}