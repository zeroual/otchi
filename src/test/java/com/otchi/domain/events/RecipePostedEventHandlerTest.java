package com.otchi.domain.events;

import com.otchi.domain.kitchen.Recipe;
import com.otchi.domain.search.RecipeSearchEngine;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


public class RecipePostedEventHandlerTest {

    private RecipePostedEventHandler recipePostedEventHandler;
    private RecipeSearchEngine recipeSearchEngine = mock(RecipeSearchEngine.class);

    @Before
    public void setUp() {
        this.recipePostedEventHandler = new RecipePostedEventHandler(recipeSearchEngine);
    }

    @Test
    public void shouldAskTheSearchEngineToIndexTheNewRecipe() {
        Recipe recipe = new Recipe("recipe_title", "recipe_desc", 50, 20);
        RecipePostedEvent recipePostedEvent = new RecipePostedEvent(recipe);
        recipePostedEventHandler.indexRecipeIntoSearchEngine(recipePostedEvent);
        verify(recipeSearchEngine).indexRecipe(recipe);
    }
}