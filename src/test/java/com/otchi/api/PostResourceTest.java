package com.otchi.api;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.otchi.api.facades.dto.IngredientDTO;
import com.otchi.api.facades.dto.InstructionDTO;
import com.otchi.api.facades.dto.RecipeDTO;
import com.otchi.domain.social.models.Post;
import com.otchi.domain.social.repositories.PostRepository;
import com.otchi.infrastructure.config.ResourcesPath;
import com.otchi.utils.AbstractControllerTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PostResourceTest extends AbstractControllerTest {


    @Autowired
    private PostRepository postRepository;

    @Test
    @DatabaseSetup("/dbunit/users/users.xml")
    public void shouldCreateNewRecipeAsPost() throws Exception {
        RecipeDTO recipeToSave = new RecipeDTO("recipe_title", "recipe_desc");

        IngredientDTO ingredientDTO = new IngredientDTO();
        ingredientDTO.setName("ingredientNam");
        ingredientDTO.setQuantity(2D);
        ingredientDTO.setUnit("unit");

        InstructionDTO instructionDTO = new InstructionDTO();
        instructionDTO.setContent("instruction");

        recipeToSave.setIngredients(Arrays.asList(ingredientDTO));
        mockMvc.perform(post(ResourcesPath.POST + "/recipe")
                .with(user("zeroual.abde@gmail.com")).with(csrf())
                .content(json(recipeToSave))
                .contentType(contentType)).andExpect(status().isCreated());
        Post savedPost = postRepository.findOne(1L);
        assertThat(savedPost).isNotNull();
        assertThat(savedPost.getRecipe().getTitle()).isEqualTo("recipe_title");
        assertThat(savedPost.getRecipe().getIngredients()).isNotEmpty();
        assertThat(savedPost.getRecipe().getInstructions()).isEmpty();
    }

    @Test
    @DatabaseSetup("/dbunit/users/users.xml")
    public void shouldAssignPostToCurrentUser() throws Exception {
        RecipeDTO recipeToSave = new RecipeDTO("recipe_title", "recipe_desc");
        mockMvc.perform(post(ResourcesPath.POST + "/recipe")
                .with(user("zeroual.abde@gmail.com")).with(csrf())
                .content(json(recipeToSave))
                .contentType(contentType))
                .andExpect(status().isCreated());
        Post savedPost = postRepository.findOne(1L);
        assertThat(savedPost.getAuthor()).isNotNull();
        assertThat(savedPost.getAuthor().getFirstName()).isEqualTo("Abdellah");
    }
}