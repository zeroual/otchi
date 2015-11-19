package com.zeros.domain.kitchen.models;


import javax.persistence.*;
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

    /**
     * time needed to be cooked in microwave, cooking table, etc.
     */
    @Column(name = "COOK_TIME")
    private int cookTime;

    /**
     * time needed to be prepared for cooking.
     */
    @Column(name = "PREP_TIME")
    private int prepTime;

    @OneToMany( targetEntity=Ingredient.class)
    private List ingredients;

    @OneToMany( targetEntity = Instruction.class )
    private List instructions;

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

    public List getInstructions() {
        return instructions;
    }

    public int getPrepTime() {
        return prepTime;
    }

    public List getIngredients() {
        return ingredients;
    }

    public int getCookTime() {
        return cookTime;
    }

    //FIXME to remove
    public void setId(Long id) {
        this.id = id;
    }
}
