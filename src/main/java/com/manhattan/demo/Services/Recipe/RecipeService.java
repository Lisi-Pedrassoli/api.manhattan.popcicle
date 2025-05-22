package com.manhattan.demo.Services.Recipe;

import com.manhattan.demo.Dtos.General.CountResponseDto;
import com.manhattan.demo.Dtos.Recipe.RecipeRequestDto;
import com.manhattan.demo.Dtos.RecipeRawMaterial.RecipeRawMaterialRequestDto;
import com.manhattan.demo.Entities.Product.ProductEntity;
import com.manhattan.demo.Entities.Recipe.RecipeEntity;
import com.manhattan.demo.Entities.RecipeRawMaterial.RecipeRawMaterialEntity;
import com.manhattan.demo.Exceptions.Product.ProductAlreadyHasRecipeException;
import com.manhattan.demo.Repositories.Recipe.RecipeRepository;
import com.manhattan.demo.Services.Product.ProductService;
import com.manhattan.demo.Services.RecipeRawMaterial.RecipeRawMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeService {
    @Autowired
    private RecipeRepository repository;
    @Autowired
    private ProductService productService;
    @Autowired
    private RecipeRawMaterialService recipeRawMaterialService;


    public RecipeEntity save(RecipeRequestDto body){
        ProductEntity product = this.productService.findById(body.produto_id());

        if(product.getRecipe() != null){
            throw new ProductAlreadyHasRecipeException();
        }

        RecipeEntity recipe = this.repository.save(new RecipeEntity(product));

        List<RecipeRawMaterialEntity> list = body.receitaMateriaPrimaList()
                .stream()
                .filter(dto -> dto.quantidade() > 0) // Filtra quantidades zero
                .map(dto ->
                        recipeRawMaterialService.save(
                                new RecipeRawMaterialRequestDto(
                                        dto.materiaPrima_id(),
                                        dto.quantidade()
                                ),
                                recipe
                        )).toList();

        if(list.isEmpty()) {
            throw new IllegalArgumentException("Pelo menos uma matéria-prima com quantidade positiva deve ser fornecida");
        }

        recipe.setReceitaMateriaPrima(list);

        return recipe;
    }

    public List<RecipeEntity> findAll(int page, int items){
        Pageable pageable = PageRequest.of(page, items);
        return this.repository.findAll(pageable).getContent();
    }

    public RecipeEntity findById(String id){
        return this.repository.findById(id).orElseThrow();
    }

    public RecipeEntity update(String id, RecipeRequestDto body){
        RecipeEntity recipe = this.findById(id);

        recipeRawMaterialService.deleteByRecipe(recipe.getId());

        recipe.getReceitaMateriaPrima().clear();
        recipe.setProduto(this.productService.findById(body.produto_id()));

        List<RecipeRawMaterialEntity> list = body.receitaMateriaPrimaList()
                .stream().map(dto ->
                        recipeRawMaterialService.save(
                                new RecipeRawMaterialRequestDto(
                                        dto.materiaPrima_id(),
                                        dto.quantidade()
                                ),
                                recipe
                        )).toList();

        recipe.setReceitaMateriaPrima(list);

        return recipe;
    }

    public CountResponseDto count(){
        return new CountResponseDto(this.repository.count());
    }
}
