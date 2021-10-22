package com.joaojmc.recipeapi.Repositories;

import com.joaojmc.recipeapi.Entities.Recipe;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface RecipeRepository extends CrudRepository<Recipe, Integer> {

    ArrayList<Recipe> findAllByCategoryIgnoreCaseOrderByDateDesc(String category);

    ArrayList<Recipe> findAllByNameContainsIgnoreCaseOrderByDateDesc(String name);
}