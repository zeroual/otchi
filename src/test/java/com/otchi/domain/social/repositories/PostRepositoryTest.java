package com.otchi.domain.social.repositories;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.otchi.domain.kitchen.models.Recipe;
import com.otchi.domain.social.models.Comment;
import com.otchi.domain.social.models.Post;
import com.otchi.utils.AbstractIntegrationTest;
import org.assertj.core.groups.Tuple;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class PostRepositoryTest extends AbstractIntegrationTest {

    @Autowired
    private PostRepository postRepository;

    @Test
    @DatabaseSetup(value = {"/dbunit/social/publications.xml"})
    public void shouldMapWithDatabase() {
        Post savedPost = postRepository.findOne(1L);
        assertThat(savedPost).isNotNull();
        assertThat(savedPost.getCreatedTime().toString()).isEqualTo("2015-02-28 00:00:00.0");
        assertThat(savedPost.getLikes()).isNotEmpty();
        assertThat(savedPost.getLikes())
                .extracting(user -> user.getFirstName())
                .contains("Abdellah");

        assertThat(savedPost.getRecipe()).isNotNull();
        assertThat(savedPost.getRecipe().getTitle()).isEqualTo("TITLE_SAMPLE_2");

        assertThat(savedPost.getAuthor()).isNotNull();
        assertThat(savedPost.getAuthor().getFirstName()).isEqualTo("Abdellah");

        assertThat(savedPost.getComments()).hasSize(1).extracting(Comment::getContent,comment -> comment.getCreatedOn().toString())
                .containsExactly(new Tuple("It is very delicious", "2016-02-22 00:00:00.0"));
        assertThat(savedPost.getComments()).hasSize(1).extracting(comment -> comment.getAuthor().getFirstName())
                .containsExactly("Abdellah");
    }

    @Test
    public void shouldSaveTheRecipeWhenSavingPost() {
        Post postToSave = new Post();
        Recipe recipe = new Recipe("recipe_title", "recipe_desc", 50, 20);
        postToSave.withRecipe(recipe);
        postRepository.save(postToSave);
        Post savedPost = postRepository.findOne(1L);
        assertThat(savedPost.getId()).isNotNull();
        assertThat(savedPost.getRecipe().getId()).isNotNull();

    }

}
