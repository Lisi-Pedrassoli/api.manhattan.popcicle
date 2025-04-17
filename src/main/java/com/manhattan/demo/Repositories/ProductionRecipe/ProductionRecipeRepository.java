package com.manhattan.demo.Repositories.ProductionRecipe;

import com.manhattan.demo.Entities.Production.ProductionEntity;
import com.manhattan.demo.Entities.ProductionRecipe.ProductionRecipeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductionRecipeRepository extends JpaRepository<ProductionRecipeEntity, String> {
        List<ProductionRecipeEntity> findByProducao(ProductionEntity producao);
}
