package com.manhattan.demo.Dtos.RawMaterial;

import com.manhattan.demo.Enums.RawMaterial.MeasurementUnitEnum;

public record RawMaterialResponseDto(
        String id,
        Float quantidadeEstoque,
        String nome,
        MeasurementUnitEnum unidadeMedida,
        boolean ativo
) {
}
