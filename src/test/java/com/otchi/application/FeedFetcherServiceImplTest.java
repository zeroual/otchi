package com.otchi.application;

import com.otchi.application.impl.FeedFetcherServiceImpl;
import com.otchi.domain.social.models.Post;
import com.otchi.domain.social.repositories.PostRepository;
import com.otchi.domain.social.repositories.mocks.MockPostRepository;
import com.otchi.domain.users.models.User;
import org.assertj.core.groups.Tuple;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static com.otchi.domain.users.models.UserBuilder.asUser;
import static java.time.LocalDateTime.parse;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.when;

public class FeedFetcherServiceImplTest {

    private PostRepository postRepository = new MockPostRepository();
    private PostMonitorService postMonitorService = Mockito.mock(PostMonitorService.class);
    private FeedFetcherService feedFetcherService = new FeedFetcherServiceImpl(postMonitorService, postRepository);

    @Before
    public void setUp() {
        postRepository.deleteAll();
        Post post1 = new Post(parse("2016-02-27T00:00:00"));
        User user1 = asUser().withUsername("author")
                .withFirstName("firstName")
                .withLastName("lastName")
                .build();
        post1.setAuthor(user1);

        Post post2 = new Post(parse("2016-02-28T00:00:00"));
        User user2 = asUser().withUsername("another author")
                .withFirstName("firstName")
                .withLastName("lastName")
                .build();
        post2.setAuthor(user2);
        postRepository.save(asList(post1, post2));
    }

    @Test
    public void shouldReturnAllFeedsAndSortThemWithCreationDateDesc() {
        List<Feed> foundFeeds = feedFetcherService.fetchAllFeeds("lambda username");
        assertThat(foundFeeds).hasSize(2);
        assertThat(foundFeeds)
                .extracting(Feed::getCreatedTime)
                .containsExactly(parse("2016-02-28T00:00:00"), parse("2016-02-27T00:00:00"))
                .isSortedAccordingTo(Comparator.reverseOrder());
    }

    @Test
    public void shouldIndicateIfUserCanOrNoRemoveFeeds() {
        List<Feed> foundFeeds = feedFetcherService.fetchAllFeeds("author");
        assertThat(foundFeeds)
                .extracting(feed -> feed.getAuthor().getUsername(), Feed::canBeRemoved)
                .contains(new Tuple("author", true), new Tuple("another author", false));
    }

    @Test
    public void shouldIndicateIfUserCanOrNoRemoveFeed() {
        Optional<Feed> foundFeed = feedFetcherService.getFeed(1L, "author");
        assertThat(foundFeed.get().canBeRemoved()).isTrue();

        foundFeed = feedFetcherService.getFeed(2L, "author");
        assertThat(foundFeed.get().canBeRemoved()).isFalse();
    }

    @Test
    public void shouldGetFeedIfExist() {
        Optional<Feed> feedOptional = feedFetcherService.getFeed(1L, "");
        assertThat(feedOptional).isPresent();
        assertThat(feedOptional.get().getCreatedTime()).isEqualTo(parse("2016-02-27T00:00:00"));

        feedOptional = feedFetcherService.getFeed(99L, "");
        assertThat(feedOptional).isEmpty();
    }

    @Test
    public void shouldSetFeedViewCount() {
        when(postMonitorService.getViewsCountOf(anyLong())).thenReturn(3);
        Optional<Feed> feedOptional = feedFetcherService.getFeed(1L, "");
        assertThat(feedOptional).isPresent();
        assertThat(feedOptional.get().getViews()).isEqualTo(3);
    }
}
