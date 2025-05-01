package com.manhattan.demo.Dtos.Seller;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.Length;

public record SellerUpdateDto(
        @Length(min = 11, max = 11)
        @NotBlank
        String telefone,
        @Positive
        @NotNull
        Float comissao,
        @NotBlank
        @Length(min = 3)
        String nome,
        @NotNull
        Boolean ativo
) {
}
