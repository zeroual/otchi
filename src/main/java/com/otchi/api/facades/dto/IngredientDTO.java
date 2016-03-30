package com.otchi.api.facades.dto;

import com.otchi.domain.kitchen.Ingredient;

public class IngredientDTO implements DTO<Ingredient> {

    private Long id;
    private String name;
    private Double quantity;
    private String unit;

    public IngredientDTO(Ingredient ingredient) {
        extractFromDomain(ingredient);
    }

    public IngredientDTO() {

    }


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Override
    public Ingredient toDomain() {
        return new Ingredient(name, quantity, unit);
    }

    @Override
    public void extractFromDomain(Ingredient ingredient) {
        this.id = ingredient.getId();
        this.name = ingredient.getName();
        this.quantity = ingredient.getQuantity();
        this.unit = ingredient.getUnit();
    }
}
