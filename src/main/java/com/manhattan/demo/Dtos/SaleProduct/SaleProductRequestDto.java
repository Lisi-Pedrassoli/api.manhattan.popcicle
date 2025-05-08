package com.manhattan.demo.Dtos.SaleProduct;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record SaleProductRequestDto(
        @NotBlank
        String productId,
        @Positive
        int quantidadeSaida,
        @NotNull
        @PositiveOrZero
        int quantidadeVolta
) {
}
