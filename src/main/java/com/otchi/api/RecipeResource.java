package com.otchi.api;

import com.otchi.config.ResourcesPath;
import com.otchi.domain.kitchen.models.Recipe;
import com.otchi.domain.kitchen.repositories.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = ResourcesPath.RECIPE)
public class RecipeResource {


    @Autowired
    private RecipeRepository recipeRepository;


    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Recipe saveRecipe(@RequestBody Recipe recipe){
        return recipeRepository.save(recipe);
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Recipe> fetchAllRecipe(){
        return recipeRepository.findAll();
    }

}
