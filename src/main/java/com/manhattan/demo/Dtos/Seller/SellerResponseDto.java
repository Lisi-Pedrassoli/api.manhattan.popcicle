package com.manhattan.demo.Dtos.Seller;

public record SellerResponseDto(
        String id,
        String telefone,
        Float comissao,
        String nome,
        Boolean ativo,
        String cpf
) {
}
