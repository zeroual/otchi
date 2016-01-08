package com.otchi.domain.kitchen.models;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "RECIPE")
public class Recipe{

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    @Column(name = "DESCRIPTION")
    private String description;


    @Column(name = "COOK_TIME")
    private int cookTime;

    @Column(name = "PREPARATION_TIME")
    private int preparationTime;

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(
            name = "RECIPE_INGREDIENT",
            joinColumns = {@JoinColumn(name = "RECIPE_ID")},
            inverseJoinColumns = {@JoinColumn(name = "INGREDIENT_ID")})
    private List<Ingredient> ingredients = new ArrayList<>();

    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "RECIPE_ID")
    private List<Instruction> instructions=new ArrayList<>();

    @Column(name = "TITLE")
    private String title;

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

    //FIXME to remove
    public void setId(Long id) {
        this.id = id;
    }


    public int getCookTime() {
        return cookTime;
    }

    public int getPreparationTime() {
        return preparationTime;
    }

    public List<Ingredient>     getIngredients() {
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
