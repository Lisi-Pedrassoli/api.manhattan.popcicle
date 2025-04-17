package com.manhattan.demo.Dtos.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import java.util.Optional;

public record UserUpdateDto(
        @Length(min = 3, max = 50)
        @NotBlank
        String nome,
        @Email
        @NotBlank
        String email,
        Optional<String> senha,
        boolean ativo
) {
}
