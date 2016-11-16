package com.otchi.domain.events;


import com.google.common.eventbus.Subscribe;
import com.otchi.domain.kitchen.Recipe;
import com.otchi.domain.search.RecipeSearchEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RecipePostedEventHandler {

    private final RecipeSearchEngine recipeSearchEngine;

    @Autowired
    public RecipePostedEventHandler(RecipeSearchEngine recipeSearchEngine) {
        this.recipeSearchEngine = recipeSearchEngine;
    }

    @Subscribe
    public void indexRecipeIntoSearchEngine(RecipePostedEvent recipePostedEvent) {
        Recipe recipe = recipePostedEvent.getRecipe();
        this.recipeSearchEngine.indexRecipe(recipe);
    }
}
