package com.otchi.domain.search;

public class RecipeSearchResult {
    private Long id;
    private String title;
    private String description;
    private Integer cookTime;
    private Integer preparationTime;

    public RecipeSearchResult() {

    }

    public RecipeSearchResult(Long id, String title, String description, Integer cookTime, Integer preparationTime) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.cookTime = cookTime;
        this.preparationTime = preparationTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCookTime() {
        return cookTime;
    }

    public void setCookTime(Integer cookTime) {
        this.cookTime = cookTime;
    }

    public Integer getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(Integer preparationTime) {
        this.preparationTime = preparationTime;
    }
}
