package com.otchi.application;

import com.otchi.application.impl.FeedFetcherServiceImpl;
import com.otchi.domain.social.models.Post;
import com.otchi.domain.social.repositories.PostRepository;
import com.otchi.domain.social.repositories.mocks.MockPostRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Dates.parse;

public class FeedFetcherServiceImplTest {

    private PostRepository postRepository = new MockPostRepository();
    private FeedFetcherService feedFetcherService = new FeedFetcherServiceImpl(postRepository);

    @Before
    public void setUp() {
        postRepository.deleteAll();
        Post post1 = new Post(parse("2016-02-27"));
        Post post2 = new Post(parse("2016-02-28"));
        postRepository.save(asList(post1, post2));
    }

    @Test
    public void shouldReturnAllPostsAndSortThemWithCreationDateDesc() {
        List<Post> foundPosts = feedFetcherService.fetchAllFeeds();
        assertThat(foundPosts).hasSize(2);
        assertThat(foundPosts)
                .extracting(Post::getCreatedTime)
                .containsExactly(parse("2016-02-28"), parse("2016-02-27"))
                .isSortedAccordingTo((o1, o2) -> o2.compareTo(o1));
    }

    @Test
    public void shouldGetPostIfExist() {
        Optional<Post> postOptional = feedFetcherService.getFeed(1L);
        assertThat(postOptional).isPresent();
        assertThat(postOptional.get().getCreatedTime()).isEqualTo(parse("2016-02-27"));

        postOptional = feedFetcherService.getFeed(99L);
        assertThat(postOptional).isEmpty();
    }
}