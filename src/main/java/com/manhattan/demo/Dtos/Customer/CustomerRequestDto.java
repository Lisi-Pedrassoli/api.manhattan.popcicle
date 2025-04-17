package com.manhattan.demo.Dtos.Customer;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record CustomerRequestDto(
        @Length(min = 11, max = 11)
        @NotBlank
        String telefone,
        @Length(min = 11, max = 14)
        @NotBlank
        String documento,
        @NotBlank
        @Length(min = 3)
        String nome
) {
}
