package com.otchi.application;

import com.otchi.application.impl.PublicationsServiceImpl;
import com.otchi.domaine.kitchen.models.Recipe;
import com.otchi.domaine.social.models.Post;
import com.otchi.domaine.social.repositories.PostRepository;
import com.otchi.domaine.social.repositories.mocks.MockPostRepository;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PublicationsServiceTest {

    private PublicationsService publicationsService;
    private PostRepository postRepository;

    @Before
    public void setUp() {
        postRepository = new MockPostRepository();
        publicationsService = new PublicationsServiceImpl(postRepository);
    }

    @Test
    public void shouldCreateNewPostWithRecipeAsContent() {
        Post post = new Post();
        Recipe recipe = new Recipe("recipe_title", "recipe_desc", 50, 20);
        post.withRecipe(recipe);
        publicationsService.createNewPost(post);
        Post savedPost = postRepository.findOne(1L);
        assertThat(savedPost).isNotNull();
        assertThat(savedPost.getRecipe()).isNotNull();
        assertThat(savedPost.getRecipe().getTitle()).isEqualTo("recipe_title");
    }
}