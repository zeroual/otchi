package com.otchi.domaine.kitchen.models;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "recipes")
public class Recipe{

    @Id
    private Long id;

    private String description;

    private Integer cookTime;

    private Integer preparationTime;

    @DBRef
    private List<Ingredient> ingredients = new ArrayList<>();

    private List<Instruction> instructions=new ArrayList<>();


    private String title;

    public Recipe(String title, String description, Integer cookTime, Integer preparationTime) {
        this.description = description;
        this.cookTime = cookTime;
        this.preparationTime = preparationTime;
        this.title = title;
    }

    public Recipe(String title, String description, Integer cookTime, Integer preparationTime, List<Ingredient> ingredients, List<Instruction> instructions) {
        this.description = description;
        this.cookTime = cookTime;
        this.preparationTime = preparationTime;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.title = title;
    }

    public Recipe() {
    }

    public Recipe(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
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

    public List<Instruction> getInstructions() {
        return instructions;
    }


    public String getTitle() {
        return title;
    }

    public void addIngredient(Ingredient ingredient) {
        this.ingredients.add(ingredient);
    }

    public void addInstruction(Instruction instruction) {
        this.instructions.add(instruction);
    }
}
