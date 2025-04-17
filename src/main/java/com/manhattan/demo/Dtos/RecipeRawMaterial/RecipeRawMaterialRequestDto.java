package com.manhattan.demo.Dtos.RecipeRawMaterial;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RecipeRawMaterialRequestDto(
        @NotBlank
        String materiaPrima_id,
        @NotNull
        Float quantidade
) {
}
