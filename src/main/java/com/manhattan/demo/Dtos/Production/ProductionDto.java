package com.manhattan.demo.Dtos.Production;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.manhattan.demo.Dtos.ProductionRecipe.ProductionRecipeDto;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

public record ProductionDto(
        @NotNull
        @DateTimeFormat(pattern = "dd/MM/yyyy")
        @JsonFormat(pattern = "dd/MM/yyyy")
        LocalDate dataAtual,

        @NotNull
        @DateTimeFormat(pattern = "dd/MM/yyyy")
        @JsonFormat(pattern = "dd/MM/yyyy")
        LocalDate vencimento,

        List<ProductionRecipeDto> receita
) {
}
