package com.manhattan.demo.Dtos.SaleProduct;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record SaleProductCloseDto(
        @NotBlank
        String produtoVendaId,
        @NotNull
        @PositiveOrZero
        int quantidadeVolta
) {
}
