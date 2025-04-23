package com.manhattan.demo.Dtos.ProductType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

public record ProductTypeRequestDto(
        @NotBlank
        @Length(min = 4, max = 50)
        String tipo,
        @NotNull
        @PositiveOrZero
        float valor
) {
}
