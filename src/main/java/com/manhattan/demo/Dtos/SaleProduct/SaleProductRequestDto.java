package com.manhattan.demo.Dtos.SaleProduct;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record SaleProductRequestDto(
        @NotBlank
        String productId,
        @Positive
        int quantidadeSaida
) {
}
