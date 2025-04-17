package com.manhattan.demo.Dtos.Customer;

public record CustomerResponseDto(
        String id,
        String telefone,
        String nome,
        Boolean ativo
) {
}
