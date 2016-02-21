package com.otchi.application;

import com.otchi.application.impl.FeedServiceImpl;
import com.otchi.domaine.social.exceptions.PostNotFoundException;
import com.otchi.domaine.social.models.Post;
import com.otchi.domaine.social.repositories.PostRepository;
import com.otchi.domaine.social.repositories.mocks.MockPostRepository;
import com.otchi.domaine.users.models.User;
import com.otchi.utils.mocks.MockCrudRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class FeedServiceImplTest {

    private PostRepository postRepository = new MockPostRepository();
    private UserService userService = Mockito.mock(UserService.class);
    private FeedService feedService = new FeedServiceImpl(postRepository, userService);
    private User user = new User("email@fofo.com", "firstName_sample", "lastName");

    @Before
    public void setUp() {
        MockCrudRepository.clearDatabase();
        Post post = new Post(new Date());
        postRepository.save(post);
        when(userService.findUserByEmail("email@fofo.com")).thenReturn(Optional.of(user));
    }

    @Test
    public void shouldAddNewLikeToPost() throws Exception {
        feedService.likePost(1L, "email@fofo.com");
        Post post = postRepository.findOne(1L);
        assertThat(post.getLikes()).hasSize(1);
        assertThat(post.getLikes())
                .extracting(user -> user.getFirstName())
                .containsExactly("firstName_sample");
    }

    @Test(expected = PostNotFoundException.class)
    public void shouldNotAllowToLikeUnExistingPost(){
        feedService.likePost(123L, "email@fofo.com");
    }

    @Test
    public void shouldNotAddLikeToPostIfAlreadyLiked() throws Exception {
        feedService.likePost(1L, "email@fofo.com");
        Post post = postRepository.findOne(1L);
        feedService.likePost(1L, "email@fofo.com");
        assertThat(post.getLikes()).hasSize(1);
    }

    @Test
    public void shouldUnlikePostIfAlreadyLiked() throws Exception {
        Post post = postRepository.findOne(1L);
        feedService.likePost(1L, "email@fofo.com");
        assertThat(post.getLikes()).hasSize(1);
        feedService.unlikePost(1L, "email@fofo.com");
        assertThat(post.getLikes()).hasSize(0);
    }
    @Test(expected = PostNotFoundException.class)
    public void shouldNotAllowToDesLikeUnExistingPost(){
        feedService.unlikePost(123L, "email@fofo.com");
    }
}