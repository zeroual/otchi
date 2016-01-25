package com.otchi.api.facades;

import com.otchi.domaine.kitchen.models.Ingredient;
import com.otchi.domaine.kitchen.models.Instruction;
import com.otchi.domaine.kitchen.models.Recipe;

import java.util.ArrayList;
import java.util.List;

public class RecipeDTO {

    private Long id;
    private String description;
    private Integer cookTime;
    private Integer preparationTime;
    private List<Ingredient> ingredients = new ArrayList<>();
    private List<Instruction> instructions = new ArrayList<>();
    private String title;

    private RecipeDTO() {

    }

    public RecipeDTO(String title, String description) {
        this.description = description;
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCookTime() {
        return cookTime;
    }

    public void setCookTime(Integer cookTime) {
        this.cookTime = cookTime;
    }

    public Integer getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(Integer preparationTime) {
        this.preparationTime = preparationTime;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Instruction> getInstructions() {
        return instructions;
    }

    public void setInstructions(List<Instruction> instructions) {
        this.instructions = instructions;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Recipe toRecipe() {
        return new Recipe(title, description, cookTime, preparationTime, ingredients, instructions);
    }
}
