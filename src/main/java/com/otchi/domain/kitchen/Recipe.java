package com.otchi.domain.kitchen;


import com.otchi.domain.social.models.PostContent;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "RECIPE")
public class Recipe extends PostContent{

    @Column(name = "DESCRIPTION")
    private String description;


    @Column(name = "COOK_TIME")
    private Integer cookTime;

    @Column(name = "PREPARATION_TIME")
    private Integer preparationTime;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "RECIPE_INGREDIENT",
            joinColumns = {@JoinColumn(name = "RECIPE_ID")},
            inverseJoinColumns = {@JoinColumn(name = "INGREDIENT_ID")})
    private List<Ingredient> ingredients = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "RECIPE_ID")
    private List<Instruction> instructions = new ArrayList<>();

    @Column(name = "TITLE")
    private String title;

    @ElementCollection
    @CollectionTable(name = "RECIPE_IMAGES", joinColumns = @JoinColumn(name = "RECIPE_ID", referencedColumnName = "ID"))
    @Column(name = "URL")
    List<String> images = new ArrayList<>();


    @ElementCollection
    private Set<String> tags = new HashSet<>();

    public Recipe(String title, String description, Integer cookTime, Integer preparationTime) {
        this.description = description;
        this.cookTime = cookTime;
        this.preparationTime = preparationTime;
        this.title = title;
    }

    public Recipe(String title, String description, Integer cookTime, Integer preparationTime, List<Ingredient> ingredients, List<Instruction> instructions, Set<String> tags) {
        this.description = description;
        this.cookTime = cookTime;
        this.preparationTime = preparationTime;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.title = title;
        this.tags = tags;
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

    public List<String> getImages() {
        return images;
    }

    public  void setImages(List<String> images) {
        this.images = images;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

}
