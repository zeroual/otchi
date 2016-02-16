package com.otchi.application;

import com.otchi.application.impl.FeedServiceImpl;
import com.otchi.domaine.social.models.Post;
import com.otchi.domaine.social.repositories.PostRepository;
import com.otchi.domaine.social.repositories.mocks.MockPostRepository;
import com.otchi.utils.mocks.MockCrudRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Reda on 06/02/2016.
 */
public class FeedServiceImplTest {

    private PostRepository postRepository = new MockPostRepository();
    private FeedService feedService = new FeedServiceImpl(postRepository);

    @Before
    public void setUp() {
        MockCrudRepository.clearDatabase();
        Post post = new Post(new Date());
        postRepository.save(post);
    }

    @Test
    public void shouldAddNewLikeToPost() throws Exception {
        feedService.likePost("1", "2");
        Post post = postRepository.findOne("1");
        assertThat(post.getLikers()).hasSize(1);
        assertThat(post.getLikers()).containsExactly("2");
    }

    @Test
    public void shouldNotAddLikeToPostIfAlreadyLiked() throws Exception {
        feedService.likePost("1", "1");
        Post post = postRepository.findOne("1");
        feedService.likePost("1", "1");
        assertThat(post.getLikers()).hasSize(1);
    }
}