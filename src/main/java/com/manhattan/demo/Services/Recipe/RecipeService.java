package com.manhattan.demo.Services.Recipe;

import com.manhattan.demo.Dtos.General.CountResponseDto;
import com.manhattan.demo.Dtos.Recipe.RecipeRequestDto;
import com.manhattan.demo.Dtos.RecipeRawMaterial.RecipeRawMaterialRequestDto;
import com.manhattan.demo.Entities.Product.ProductEntity;
import com.manhattan.demo.Entities.Recipe.RecipeEntity;
import com.manhattan.demo.Entities.RecipeRawMaterial.RecipeRawMaterialEntity;
import com.manhattan.demo.Exceptions.Product.ProductAlreadyHasRecipeException;
import com.manhattan.demo.Repositories.Recipe.RecipeRepository;
import com.manhattan.demo.Services.Log.LogService;
import com.manhattan.demo.Services.Product.ProductService;
import com.manhattan.demo.Services.RecipeRawMaterial.RecipeRawMaterialService;
import jakarta.transaction.Transactional;
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

    @Autowired
    private LogService logService;

    public RecipeEntity save(RecipeRequestDto body, String usuarioId) {
        ProductEntity product = this.productService.findById(body.produto_id());

        if (product.getRecipe() != null) {
            throw new ProductAlreadyHasRecipeException();
        }

        RecipeEntity recipe = this.repository.save(new RecipeEntity(product));

        List<RecipeRawMaterialEntity> list = body.receitaMateriaPrimaList()
                .stream()
                .filter(dto -> dto.quantidade() > 0)
                .map(dto ->
                        recipeRawMaterialService.save(
                                new RecipeRawMaterialRequestDto(
                                        dto.materiaPrima_id(),
                                        dto.quantidade()
                                ),
                                recipe
                        )
                ).toList();

        if (list.isEmpty()) {
            throw new IllegalArgumentException("Pelo menos uma matéria-prima com quantidade positiva deve ser fornecida");
        }

        recipe.setReceitaMateriaPrima(list);

        logService.registrar(usuarioId, "Cadastro de receita", "Produto ID: " + product.getId());

        return recipe;
    }

    @Transactional
    public RecipeEntity update(String id, RecipeRequestDto body, String usuarioId) {
        RecipeEntity recipe = this.findById(id);

        // Remove matérias-primas antigas
        recipe.getReceitaMateriaPrima().clear();


        // Salva as novas matérias-primas
        List<RecipeRawMaterialEntity> list = body.receitaMateriaPrimaList()
                .stream().map(dto ->
                        recipeRawMaterialService.save(
                                new RecipeRawMaterialRequestDto(
                                        dto.materiaPrima_id(),
                                        dto.quantidade()
                                ),
                                recipe
                        )
                ).toList();

        recipe.getReceitaMateriaPrima().addAll(list);

        logService.registrar(usuarioId, "Atualização de receita", "ID da receita: " + id);

        return recipe;
    }

    public List<RecipeEntity> findAll(int page, int items) {
        Pageable pageable = PageRequest.of(page, items);
        return this.repository.findAll(pageable).getContent();
    }

    public RecipeEntity findById(String id) {
        return this.repository.findById(id).orElseThrow();
    }

    public CountResponseDto count() {
        return new CountResponseDto(this.repository.count());
    }

    public void delete(String id, String usuarioId) {
        RecipeEntity recipe = this.findById(id);
        recipe.setAtivo(false);
        this.repository.save(recipe);

        logService.registrar(
                usuarioId,
                "Exclusão de receita",
                "ID: " + id + ", nome do produto: " + recipe.getProduto().getNome()
        );
    }
}