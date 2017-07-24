package com.otchi.api;

import com.otchi.domain.kitchen.TodayRecipe;
import com.otchi.domain.kitchen.TodayRecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class TodayRecipeController {

    private final TodayRecipeRepository todayRecipeRepository;

    @Autowired
    public TodayRecipeController(TodayRecipeRepository todayRecipeRepository) {
        this.todayRecipeRepository = todayRecipeRepository;
    }

    @GetMapping("/api/v1/today-recipe")
    public Optional<TodayRecipe> getTodayRecipe() {
        return todayRecipeRepository.findFirstByOrderByDateDesc();
    }
}
