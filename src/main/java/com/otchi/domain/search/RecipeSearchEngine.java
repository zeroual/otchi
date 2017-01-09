package com.otchi.domain.search;

import com.otchi.domain.kitchen.Recipe;

import java.util.List;

public interface RecipeSearchEngine {

    void indexRecipe(Recipe recipe);

    List<RecipeSearchSuggestion> suggestRecipes(String query);

    List<RecipeSearchResult> searchRecipes(String query);
}
