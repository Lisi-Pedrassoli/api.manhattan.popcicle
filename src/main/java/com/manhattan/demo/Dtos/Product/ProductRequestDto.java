package com.manhattan.demo.Dtos.Product;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record ProductRequestDto(
        @NotBlank
        String nome,
        @NotNull
        @PositiveOrZero
        Integer estoque,
        @NotBlank
        String tipoProdutoId
) {
}
