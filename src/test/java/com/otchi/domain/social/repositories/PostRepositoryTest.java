package com.otchi.domain.social.repositories;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.otchi.domain.kitchen.Recipe;
import com.otchi.domain.social.models.Comment;
import com.otchi.domain.social.models.Post;
import com.otchi.domain.social.models.Story;
import com.otchi.utils.AbstractIntegrationTest;
import org.assertj.core.groups.Tuple;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.parse;
import static org.assertj.core.api.Assertions.assertThat;

public class PostRepositoryTest extends AbstractIntegrationTest {

    @Autowired
    private PostRepository postRepository;


    @Test
    @DatabaseSetup(value = {"/dbunit/social/publications.xml"})
    public void shouldMapWithDatabase() {
        Post savedPost = postRepository.findOne(1L);
        assertThat(savedPost).isNotNull();
        assertThat(savedPost.getCreatedTime()).isEqualTo(parse("2015-02-28T00:00:00"));
        assertThat(savedPost.getLikes()).isNotEmpty();
        assertThat(savedPost.getLikes())
                .extracting(user -> user.getFirstName())
                .contains("Abdellah");

        assertThat(savedPost.getPostContent()).isNotNull();
        assertThat(savedPost.images()).containsOnly("http://url.com/image1");
        assertThat(((Recipe) savedPost.getPostContent()).getTitle()).isEqualTo("TITLE_SAMPLE_2");

        assertThat(savedPost.getAuthor()).isNotNull();
        assertThat(savedPost.getAuthor().getFirstName()).isEqualTo("Abdellah");

        assertThat(savedPost.getComments()).hasSize(1).extracting(Comment::getContent, comment -> comment.getCreatedOn())
                .containsExactly(new Tuple("It is very delicious", parse("2016-02-22T00:00:00")));
        assertThat(savedPost.getComments()).hasSize(1).extracting(comment -> comment.getAuthor().getFirstName())
                .containsExactly("Abdellah");

        Post savedStoryPost = postRepository.findOne(2L);
        assertThat(savedStoryPost).isNotNull();
        assertThat(((Story) savedStoryPost.getPostContent()).content()).isEqualTo("my story");
        assertThat(savedStoryPost.images()).containsOnly("http://url.com/image2");

    }

    @Test
    @DatabaseSetup(value = {"/dbunit/kitchen/empty.xml"})
    public void shouldSaveTheRecipeWhenSavingPost() {
        Post postToSave = new Post();
        Recipe recipe = new Recipe("recipe_title", "recipe_desc", 50, 20);
        postToSave.setPostContent(recipe);
        postRepository.save(postToSave);
        Post savedPost = postRepository.findOne(1L);
        assertThat(savedPost.getId()).isNotNull();
        assertThat((savedPost.getPostContent()).getId()).isNotNull();

    }

    @Test
    @DatabaseSetup(value = {"/dbunit/social/today-recipes.xml"})
    public void shouldFindRecipeTheMostViewedAfterADate() {
        LocalDateTime yesterday = LocalDateTime.parse("2017-02-27T00:00:00");
        Post recipePostMostLiked = postRepository.findFirstRecipePostMostLikedAfter(yesterday).get();
        assertThat(recipePostMostLiked.getId()).isEqualTo(1L);
    }

    @Test
    @DatabaseSetup(value = {"/dbunit/social/today-recipes.xml"})
    public void shouldFindRecipeTheMostViewedAndReturnOnlyOne() {
        LocalDateTime yesterday = LocalDateTime.parse("2017-03-01T00:00:00");
        Post recipePostMostLiked = postRepository.findFirstRecipePostMostLikedAfter(yesterday).get();
        assertThat(recipePostMostLiked.getId()).isEqualTo(3L);
    }


}
