package com.zeros.domain.kitchen.repositories;

import com.zeros.domain.kitchen.models.Recipe;

import java.util.HashMap;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class RecipeRepository {

    private HashMap<Long, Recipe> db = new HashMap<>();

    public Recipe save(Recipe recipe) {
        recipe.setId((long) (db.size() + 1));
        db.put(recipe.getId(), recipe);
        return recipe;
    }

    public Recipe findOne(Long id) {
        return db.get(id);
    }

    public List<Recipe> findAll() {
        return db.keySet().stream().map(db::get).collect(toList());
    }
}
