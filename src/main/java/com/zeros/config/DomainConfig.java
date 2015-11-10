package com.zeros.config;


import com.zeros.domain.kitchen.repositories.RecipeRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class DomainConfig {

    @Bean
    public RecipeRepository recipeRepository(){
        return new RecipeRepository();
    }

}
