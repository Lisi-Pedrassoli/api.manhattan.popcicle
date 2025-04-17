package com.manhattan.demo.Dtos.Sale;

import com.manhattan.demo.Dtos.SaleProduct.SaleProductCloseDto;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CloseSaleDto(
        @NotNull
        List<SaleProductCloseDto> produtosVenda
) {
}
