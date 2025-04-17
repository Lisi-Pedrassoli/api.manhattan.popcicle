package com.manhattan.demo.Services.RecipeRawMaterial;

import com.manhattan.demo.Dtos.RecipeRawMaterial.RecipeRawMaterialRequestDto;
import com.manhattan.demo.Entities.Recipe.RecipeEntity;
import com.manhattan.demo.Entities.RecipeRawMaterial.RecipeRawMaterialEntity;
import com.manhattan.demo.Repositories.RecipeRawMaterial.RecipeRawMaterialRepository;
import com.manhattan.demo.Services.RawMaterial.RawMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecipeRawMaterialService {
    @Autowired
    private RecipeRawMaterialRepository repository;

    @Autowired
    private RawMaterialService rawMaterialService;

    public RecipeRawMaterialEntity save(RecipeRawMaterialRequestDto body, RecipeEntity recipe){
        return this.repository.save(
                new RecipeRawMaterialEntity(
                        body.quantidade(), this.rawMaterialService.findById(body.materiaPrima_id()), recipe)
        );
    }

    public void deleteByRecipe(String recipeId){
        this.repository.deleteByRecipe(recipeId);
    }
}
