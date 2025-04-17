package com.manhattan.demo.Dtos.Seller;

public record SellerResponseDto(
        String id,
        String telefone,
        Float recebimento,
        String nome,
        Boolean ativo
) {
}
