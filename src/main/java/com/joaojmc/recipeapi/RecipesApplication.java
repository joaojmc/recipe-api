package com.joaojmc.recipeapi;

import com.joaojmc.recipeapi.Controllers.RecipeController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import(RecipeController.class)
@SpringBootApplication
public class RecipesApplication {
    public static void main(String[] args) {
        SpringApplication.run(RecipesApplication.class, args);
    }
}
