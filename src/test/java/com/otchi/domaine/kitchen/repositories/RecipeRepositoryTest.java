package com.otchi.domaine.kitchen.repositories;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.otchi.domaine.kitchen.models.Ingredient;
import com.otchi.domaine.kitchen.models.Instruction;
import com.otchi.domaine.kitchen.models.Recipe;
import com.otchi.utils.AbstractRepositoryTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class RecipeRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private RecipeRepository recipeRepository;

    @Test
    @DatabaseSetup("/dbunit/kitchen/recipes.xml")
    public void shouldMapWithDataBase() {
        Recipe recipe = recipeRepository.findOne("1");
        assertThat(recipe.getDescription()).isEqualTo("toto");
        assertThat(recipe.getTitle()).isEqualTo("TITLE_SAMPLE");
        assertThat(recipe.getCookTime()).isEqualTo(12);
        assertThat(recipe.getPreparationTime()).isEqualTo(20);
        assertThat(recipe.getIngredients())
                .extracting(ingredient -> ingredient.getName())
                .isEqualTo(asList("ingredient_name"));
        assertThat(recipe.getIngredients())
                .extracting(ingredient -> ingredient.getQuantity())
                .isEqualTo(asList(2.2));

        assertThat(recipe.getIngredients())
                .extracting(ingredient -> ingredient.getUnit())
                .isEqualTo(asList("KG"));

        assertThat(recipe.getInstructions())
                .extracting(instruction -> instruction.getContent())
                .isEqualTo(asList("CONTENT_SAMPLE"));
    }

    @Test
    @DatabaseSetup("/dbunit/kitchen/empty.xml")
    public void shouldSaveRecipe() {
        Recipe recipe = new Recipe("description");
        Ingredient ingredient=new Ingredient("carrot",2D,"KG");
        recipe.addIngredient(ingredient);
        Instruction instruction = new Instruction("boil carrots");
        recipe.addInstruction(instruction);
        recipeRepository.save(recipe);

        Recipe savedRecipe = recipeRepository.findOne("1");
        assertThat(savedRecipe).isNotNull();
        assertThat(savedRecipe.getInstructions()).isNotEmpty();
        assertThat(savedRecipe.getInstructions()).isNotEmpty();


    }

}
