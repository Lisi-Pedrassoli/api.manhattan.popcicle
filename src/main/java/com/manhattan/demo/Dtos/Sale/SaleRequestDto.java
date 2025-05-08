package com.manhattan.demo.Dtos.Sale;
import com.manhattan.demo.Dtos.SaleProduct.SaleProductRequestDto;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

public record SaleRequestDto(
        Optional<String> clienteId,
        Optional<String> vendedorId,
        @NotNull
        List<SaleProductRequestDto> produtoVenda
){
}
