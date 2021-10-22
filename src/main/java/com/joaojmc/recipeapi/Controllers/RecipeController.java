package com.joaojmc.recipeapi.Controllers;

import com.joaojmc.recipeapi.Entities.APIResponse;
import com.joaojmc.recipeapi.Entities.Recipe;
import com.joaojmc.recipeapi.Repositories.RecipeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class RecipeController {

    final
    RecipeRepository recipeRepository;

    final
    AuthController authController;

    public RecipeController(RecipeRepository recipeRepository, AuthController authController) {
        this.recipeRepository = recipeRepository;
        this.authController = authController;
    }

    @GetMapping("recipe/{id}")
    public Recipe getRecipe(@PathVariable Integer id) {
        var dbRecipe = recipeRepository.findById(id);
        return dbRecipe.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found"));
    }

    @GetMapping(value = "recipe/search", params = "category")
    public List<Recipe> getRecipesByCategory(@RequestParam String category) {
        if (category == null || category.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        var dbRecipes = new ArrayList<Recipe>();

        if (!category.isBlank()) {
            dbRecipes = recipeRepository.findAllByCategoryIgnoreCaseOrderByDateDesc(category);
        }

        return dbRecipes.isEmpty() ? Collections.emptyList() : dbRecipes;
    }

    @GetMapping(value = "recipe/search", params = "name")
    public List<Recipe> getRecipesByName(@RequestParam String name) {
        if (name == null || name.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        var dbRecipes = new ArrayList<Recipe>();

        if (!name.isBlank()) {
            dbRecipes = recipeRepository.findAllByNameContainsIgnoreCaseOrderByDateDesc(name);
        }

        return dbRecipes.isEmpty() ? Collections.emptyList() : dbRecipes;
    }

    @PostMapping("recipe/new")
    public APIResponse postRecipe(@RequestBody @Valid Recipe recipe) {
        recipe.setDate(LocalDateTime.now());
        recipe.setAuthor(authController.currentUserNameSimple());
        var dbRecipe = recipeRepository.save(recipe);
        return new APIResponse(dbRecipe.getId());
    }

    @PutMapping("recipe/{id}")
    public ResponseEntity<Recipe> putRecipe(@PathVariable Integer id, @RequestBody @Valid Recipe recipe) {
        var dbRecipe = recipeRepository.findById(id);

        if (dbRecipe.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found");

        if (!dbRecipe.get().getAuthor().equals(authController.currentUserNameSimple())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Forbidden");
        }

        updateRecipe(dbRecipe.get(), recipe);
        recipeRepository.save(dbRecipe.get());

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("recipe/{id}")
    public ResponseEntity<Recipe> deleteRecipe(@PathVariable int id) {
        var dbRecipe = recipeRepository.findById(id);
        if (dbRecipe.isPresent()) {

            if (!dbRecipe.get().getAuthor().equals(authController.currentUserNameSimple())) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Forbidden");
            }

            recipeRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found");
        }
    }

    private void updateRecipe(Recipe original, Recipe update) {
        if (!original.getName().equals(update.getName())) original.setName(update.getName());
        if (!original.getDescription().equals(update.getDescription()))
            original.setDescription(update.getDescription());
        if (!original.getCategory().equals(update.getCategory())) original.setCategory(update.getCategory());
        if (!original.getIngredients().equals(update.getIngredients()))
            original.setIngredients(update.getIngredients());
        if (!original.getDirections().equals(update.getDirections())) original.setDirections(update.getDirections());
        update.setDate(LocalDateTime.now());
    }
}

