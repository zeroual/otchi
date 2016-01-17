package com.otchi.api;

import com.otchi.api.facades.RecipeDTO;
import com.otchi.application.utils.DateFactory;
import com.otchi.domaine.social.models.Post;
import com.otchi.domaine.social.repositories.PostRepository;
import com.otchi.infrastructure.config.ResourcesPath;
import com.otchi.utils.AbstractControllerTest;
import com.otchi.utils.mocks.MockDateFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PostResourceTest extends AbstractControllerTest {


    @Autowired
    private PostRepository postRepository;

    @Autowired
    private DateFactory dateFactory;

    @Test
    public void shouldCreateNewRecipeAsPost() throws Exception {
        RecipeDTO recipeToSave = new RecipeDTO("recipe_title", "recipe_desc");
        mockMvc.perform(post(ResourcesPath.POST + "/recipe")
                .content(json(recipeToSave))
                .contentType(contentType)).andExpect(status().isCreated());
        Post savedPost = postRepository.findOne(1L);
        assertThat(savedPost).isNotNull();
        assertThat(savedPost.getRecipe().getTitle()).isEqualTo("recipe_title");
    }

    @Test
    public void shouldAssignPostCreationDateToNow() throws Exception {
        Date now = new SimpleDateFormat("yyyy-MM-dd").parse("2015-02-28");
        ((MockDateFactory) dateFactory).setNow(now);
        RecipeDTO recipeToSave = new RecipeDTO("recipe_title", "recipe_desc");
        mockMvc.perform(post(ResourcesPath.POST + "/recipe")
                .content(json(recipeToSave))
                .contentType(contentType)).andExpect(status().isCreated());
        Post savedPost = postRepository.findOne(1L);
        assertThat(savedPost.getCreationDate()).isEqualTo(now);
    }
}