package com.otchi.domain.events;


import com.otchi.domain.kitchen.Recipe;

public class RecipePostedEvent implements Event {

    private final Recipe recipe;

    public RecipePostedEvent(Recipe recipe) {
        this.recipe = recipe;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RecipePostedEvent that = (RecipePostedEvent) o;

        return recipe != null ? recipe.equals(that.recipe) : that.recipe == null;

    }

}
