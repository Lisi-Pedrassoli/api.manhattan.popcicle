package com.manhattan.demo.Dtos.Authorization;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record RegisterRequestDto(
        @Length(min = 3, max = 50)
        @NotBlank
        String nome,
        @Email
        @NotBlank
        String email,
        @NotBlank
        String senha,
        @NotBlank
        String confirmaSenha
) {
}