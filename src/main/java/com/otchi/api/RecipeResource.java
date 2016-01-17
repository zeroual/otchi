package com.otchi.api;

import com.otchi.domaine.kitchen.models.Recipe;
import com.otchi.domaine.kitchen.repositories.RecipeRepository;
import com.otchi.infrastructure.config.ResourcesPath;
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
