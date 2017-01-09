package com.otchi.api;


import com.otchi.domain.search.RecipeSearchEngine;
import com.otchi.domain.search.RecipeSearchResult;
import com.otchi.domain.search.RecipeSearchSuggestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.otchi.infrastructure.config.ResourcesPath.SEARCH_RECIPE;
import static com.otchi.infrastructure.config.ResourcesPath.SEARCH_SUGGEST_RECIPE;


@RestController
public class RecipeSearchResource {

    @Autowired
    private RecipeSearchEngine recipeSearchEngine;

    @RequestMapping(value = SEARCH_SUGGEST_RECIPE, method = RequestMethod.GET)
    public List<RecipeSearchSuggestion> suggestRecipes(@RequestParam("query") String query) {
        return recipeSearchEngine.suggestRecipes(query);
    }

    @RequestMapping(value = SEARCH_RECIPE, method = RequestMethod.GET)
    public List<RecipeSearchResult> searchRecipes(@RequestParam("query") String query) {
        return recipeSearchEngine.searchRecipes(query);
    }
}
