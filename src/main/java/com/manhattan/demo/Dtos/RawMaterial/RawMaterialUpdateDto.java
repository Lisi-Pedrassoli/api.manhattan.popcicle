package com.manhattan.demo.Dtos.RawMaterial;

import com.manhattan.demo.Enums.RawMaterial.MeasurementUnitEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record RawMaterialUpdateDto(
        @NotNull
        @PositiveOrZero
        Float quantidadeEstoque,
        @NotBlank
        String nome,
        @NotNull
        MeasurementUnitEnum unidadeMedida,
        @NotNull
        Boolean ativo
) {
}
