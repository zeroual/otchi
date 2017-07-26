package com.otchi.application;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.otchi.domain.kitchen.TodayRecipe;
import com.otchi.domain.kitchen.TodayRecipeRepository;
import com.otchi.utils.AbstractIntegrationTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class TodayRecipeRepositoryTest extends AbstractIntegrationTest {

    @Autowired
    TodayRecipeRepository todayRecipeRepository;

    @Test
    @DatabaseSetup("/dbunit/social/today-recipe.xml")
    public void shouldFindTheLatRecipeRepository() {
        Optional<TodayRecipe> todayRecipe = todayRecipeRepository.findFirstByOrderByDateDesc();
        assertThat(todayRecipe.get().getPostId()).isEqualTo(24L);
    }
}