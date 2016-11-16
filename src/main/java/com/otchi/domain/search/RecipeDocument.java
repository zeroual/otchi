package com.otchi.domain.search;

import com.otchi.domain.kitchen.Ingredient;
import com.otchi.domain.kitchen.Recipe;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RecipeDocument {

    private Long id;
    private String title;
    private String description;
    private Integer cookTime;
    private Integer preparationTime;
    private List<Ingredient> ingredients = new ArrayList<>();
    private Set<String> tags = new HashSet<>();

    private RecipeDocument() {
    }

    public RecipeDocument(Recipe recipe) {
        this.id = recipe.getId();
        this.title = recipe.getTitle();
        this.description = recipe.getDescription();
        this.cookTime = recipe.getCookTime();
        this.preparationTime = recipe.getPreparationTime();
        this.ingredients = recipe.getIngredients();
        this.tags = recipe.getTags();
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Integer getCookTime() {
        return cookTime;
    }

    public Integer getPreparationTime() {
        return preparationTime;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
