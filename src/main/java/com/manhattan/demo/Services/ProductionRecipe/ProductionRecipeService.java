package com.manhattan.demo.Services.ProductionRecipe;

import com.manhattan.demo.Entities.Production.ProductionEntity;
import com.manhattan.demo.Entities.ProductionRecipe.ProductionRecipeEntity;
import com.manhattan.demo.Repositories.ProductionRecipe.ProductionRecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductionRecipeService {
    @Autowired
    private ProductionRecipeRepository repository;

    public List<ProductionRecipeEntity> findByProduction(ProductionEntity production){
        return this.repository.findByProducao(production);
    }

    public ProductionRecipeEntity save(ProductionRecipeEntity body){
        return this.repository.save(body);
    }
}
