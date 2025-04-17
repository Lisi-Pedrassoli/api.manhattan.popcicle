package com.manhattan.demo.Dtos.Product;

import com.manhattan.demo.Dtos.ProductType.ProductTypeResponseDto;

public record ProductResponseDto(
        String id,
        String nome,
        int estoque,
        boolean ativo,
        ProductTypeResponseDto tipoProduto
) {
}
