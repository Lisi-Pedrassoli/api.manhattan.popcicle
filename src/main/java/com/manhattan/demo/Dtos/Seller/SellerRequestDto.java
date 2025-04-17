package com.manhattan.demo.Dtos.Seller;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.Length;

public record SellerRequestDto(
        @Length(min = 11, max = 11)
        @NotBlank
        String telefone,
        @Positive
        @NotNull
        Float recebimento,
        @Length(min = 11, max = 11)
        @NotBlank
        String cpf,
        @NotBlank
        @Length(min = 3)
        String nome
) {
}
