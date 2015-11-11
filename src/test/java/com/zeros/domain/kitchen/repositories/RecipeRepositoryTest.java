package com.zeros.domain.kitchen.repositories;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.zeros.domain.kitchen.models.Recipe;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
public class RecipeRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private RecipeRepository recipeRepository;

    @Test
    @DatabaseSetup("/dbunit/kitchen/recipes.xml")
    public void shouldMapWithDataBase() {
        Recipe recipe = recipeRepository.findOne(1L);
        Assertions.assertThat(recipe.getDescription()).isEqualTo("toto");
    }

}
