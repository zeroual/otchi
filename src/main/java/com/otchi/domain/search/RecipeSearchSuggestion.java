package com.otchi.domain.search;

public class RecipeSearchSuggestion {

    private String title;

    private RecipeSearchSuggestion() {
    }

    public RecipeSearchSuggestion(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

