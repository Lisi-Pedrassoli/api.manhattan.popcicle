package com.manhattan.demo.Dtos.Recipe;

import com.manhattan.demo.Dtos.RecipeRawMaterial.RecipeRawMaterialRequestDto;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record RecipeRequestDto(
        @NotNull
        String produto_id,
        @NotNull
        List<RecipeRawMaterialRequestDto> receitaMateriaPrimaList
) {
}
