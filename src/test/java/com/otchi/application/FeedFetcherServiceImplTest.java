package com.otchi.application;

import com.otchi.application.impl.FeedFetcherServiceImpl;
import com.otchi.domaine.social.models.Post;
import com.otchi.domaine.social.repositories.PostRepository;
import com.otchi.domaine.social.repositories.mocks.MockPostRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Dates.parse;

public class FeedFetcherServiceImplTest {

    private PostRepository postRepository = new MockPostRepository();
    private FeedFetcherService feedFetcherService = new FeedFetcherServiceImpl(postRepository);

    @Before
    public void setUp() {

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
}