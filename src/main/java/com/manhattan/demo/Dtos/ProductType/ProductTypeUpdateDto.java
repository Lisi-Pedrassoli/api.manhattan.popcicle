package com.manhattan.demo.Dtos.ProductType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import org.hibernate.validator.constraints.Length;

public record ProductTypeUpdateDto(
        @NotBlank
        @Length(min = 4, max = 50)
        String tipo,
        @PositiveOrZero
        @NotNull
        Float valor,
        @NotNull
        Boolean ativo
) {
}
