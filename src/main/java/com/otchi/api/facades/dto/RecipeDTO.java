package com.otchi.api.facades.dto;

import com.otchi.domain.kitchen.Ingredient;
import com.otchi.domain.kitchen.Instruction;
import com.otchi.domain.kitchen.Recipe;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class RecipeDTO extends AbstractPostContent implements DTO<Recipe> {

    private Long id;
    private String description;
    private Integer cookTime;
    private Integer preparationTime;
    private List<IngredientDTO> ingredients = new ArrayList<>();
    private List<InstructionDTO> instructions = new ArrayList<>();
    private String title;

    private RecipeDTO() {
        super("RECIPE");

    }

    public RecipeDTO(String title, String description) {
        super("RECIPE");
        this.description = description;
        this.title = title;
    }

    public RecipeDTO(Recipe recipe) {
        super("RECIPE");
        extractFromDomain(recipe);


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

    public List<IngredientDTO> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<IngredientDTO> ingredients) {
        this.ingredients = ingredients;
    }

    public List<InstructionDTO> getInstructions() {
        return instructions;
    }

    public void setInstructions(List<InstructionDTO> instructions) {
        this.instructions = instructions;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public Recipe toDomain() {

        List<Ingredient> ingredientList = ingredients.stream()
                .map(IngredientDTO::toDomain)
                .collect(toList());
        List<Instruction> instructionList = instructions.stream()
                .map(InstructionDTO::toDomain)
                .collect(toList());
        return new Recipe(title, description, cookTime, preparationTime, ingredientList, instructionList);
    }

    @Override
    public void extractFromDomain(Recipe recipe) {
        this.id = recipe.getId();
        this.description = recipe.getDescription();
        this.cookTime = recipe.getCookTime();
        this.preparationTime = recipe.getPreparationTime();
        this.ingredients = recipe.getIngredients().stream()
                .map(IngredientDTO::new)
                .collect(toList());

        this.instructions = recipe.getInstructions().stream()
                .map(InstructionDTO::new).collect(toList());
        this.title = recipe.getTitle();
    }
}
