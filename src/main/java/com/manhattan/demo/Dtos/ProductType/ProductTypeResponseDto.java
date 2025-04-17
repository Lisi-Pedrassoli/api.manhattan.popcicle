package com.manhattan.demo.Dtos.ProductType;

public record ProductTypeResponseDto(
        String id,
        String tipo,
        float valor,
        boolean ativo
) {
}
