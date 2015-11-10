package com.zeros.api;
import com.zeros.config.ResourcesPath;
import com.zeros.domain.kitchen.models.Recipe;
import com.zeros.domain.kitchen.repositories.RecipeRepository;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RecipeControllerTest extends  AbstractControllerTest{


    @Autowired
    private RecipeRepository recipeRepository;

   @Test
    public void shouldCreateNewRecipe() throws Exception {
       Recipe unSavedRecipe=new Recipe("toto");
        mockMvc.perform(post(ResourcesPath.RECIPE)
                        .content(json(unSavedRecipe))
                        .contentType(contentType)).andExpect(status().isCreated());

       Recipe savedRecipe=recipeRepository.findOne(1L);
       Assert.assertNotNull(savedRecipe);

   }

    @Test
    public void shouldFetchAllRecipes() throws Exception {
        Recipe recipe1=new Recipe("toto1");
        Recipe recipe2=new Recipe("toto2");

        recipeRepository.save(recipe1);
        recipeRepository.save(recipe2);

        mockMvc.perform(get(ResourcesPath.RECIPE))
                .andExpect(content().json("[{\"description\":\"toto1\",\"id\":1},{\"description\":\"toto2\",\"id\":2}]"))
                .andExpect(status().isOk());

    }
}
