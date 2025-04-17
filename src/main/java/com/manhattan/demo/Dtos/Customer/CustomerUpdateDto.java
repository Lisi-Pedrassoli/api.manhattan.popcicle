package com.manhattan.demo.Dtos.Customer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record CustomerUpdateDto(
        @Length(min = 11, max = 11)
        @NotBlank
        String telefone,
        @NotBlank
        @Length(min = 3)
        String nome,
        @NotNull
        Boolean ativo
) {
}
