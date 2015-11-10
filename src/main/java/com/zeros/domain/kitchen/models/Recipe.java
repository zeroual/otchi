package com.zeros.domain.kitchen.models;


public class Recipe{

    private String description;
    protected Long id;

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
}
