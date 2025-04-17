package com.manhattan.demo.Repositories.RecipeRawMaterial;

import com.manhattan.demo.Entities.RecipeRawMaterial.RecipeRawMaterialEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeRawMaterialRepository extends JpaRepository<RecipeRawMaterialEntity, String> {
    @Modifying
    @Query(value = "DELETE FROM receita_materia_prima WHERE receita_id = :receitaId", nativeQuery = true)
    void deleteByRecipe(@Param("receitaId") String recipeId);
}
