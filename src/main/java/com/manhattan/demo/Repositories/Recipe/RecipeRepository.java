package com.manhattan.demo.Repositories.Recipe;

import com.manhattan.demo.Entities.Recipe.RecipeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeRepository extends JpaRepository<RecipeEntity, String> {
}
