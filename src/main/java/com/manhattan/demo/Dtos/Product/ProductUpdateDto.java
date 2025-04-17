package com.manhattan.demo.Dtos.Product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record ProductUpdateDto(
        @NotBlank
        String nome,
        @NotNull
        @PositiveOrZero
        int estoque,
        @NotNull
        Boolean ativo,
        @NotBlank
        String tipoProdutoId
) {
}
