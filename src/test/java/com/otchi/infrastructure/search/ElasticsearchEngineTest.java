package com.otchi.infrastructure.search;

import com.otchi.domain.kitchen.Recipe;
import com.otchi.domain.search.RecipeSearchEngine;
import com.otchi.domain.search.RecipeSearchResult;
import com.otchi.domain.search.RecipeSearchSuggestion;
import com.otchi.utils.AbstractIntegrationTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.util.ReflectionTestUtils.setField;

@Transactional
public class ElasticsearchEngineTest extends AbstractIntegrationTest {

    @Autowired
    private RecipeSearchEngine recipeSearchEngine;

    @Before
    public void initData() {

        String description = "Homemade Italian Lasagna, made with homemade sauce";
        String title = "Italian Lasagna";
        Recipe recipe = new Recipe(title, description, 12, 23);
        setField(recipe, "id", 1L);
        recipeSearchEngine.indexRecipe(recipe);
    }

    @Test
    public void shouldSuggestRecipesWithTitle() {
        List<RecipeSearchSuggestion> recipeSearchSuggestions = recipeSearchEngine.suggestRecipes("ital");
        assertThat(recipeSearchSuggestions).hasSize(1);
        assertThat(recipeSearchSuggestions).extracting(RecipeSearchSuggestion::getTitle)
                .containsExactly("Italian Lasagna");
    }

    @Test
    public void shouldSearchRecipeByTitle() {
        List<RecipeSearchResult> recipeSearchResults = recipeSearchEngine.searchRecipes("italian");
        assertThat(recipeSearchResults).hasSize(1);
        assertThat(recipeSearchResults).extracting(RecipeSearchResult::getTitle)
                .containsExactly("Italian Lasagna");
    }
}