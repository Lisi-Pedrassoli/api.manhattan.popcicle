package com.manhattan.demo.Services.ProductionRecipe;

import com.manhattan.demo.Entities.Production.ProductionEntity;
import com.manhattan.demo.Entities.ProductionRecipe.ProductionRecipeEntity;
import com.manhattan.demo.Repositories.ProductionRecipe.ProductionRecipeRepository;
import com.manhattan.demo.Services.Log.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductionRecipeService {

    @Autowired
    private ProductionRecipeRepository repository;

    @Autowired
    private LogService logService;

    public List<ProductionRecipeEntity> findByProduction(ProductionEntity production){
        return this.repository.findByProducao(production);
    }

    public ProductionRecipeEntity save(ProductionRecipeEntity body, String usuarioId){
        ProductionRecipeEntity entity = this.repository.save(body);

        logService.registrar(
                usuarioId,
                "Vincular receita à produção",
                "Produção ID: " + entity.getProducao().getId() +
                        ", Receita ID: " + entity.getReceita().getId() +
                        ", Quantidade: " + entity.getQuantidadeProduzida()
        );

        return entity;
    }
}