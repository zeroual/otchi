package com.otchi.application;

import com.otchi.application.impl.FeedServiceImpl;
import com.otchi.application.utils.DateFactory;
import com.otchi.domain.events.DomainEvents;
import com.otchi.domain.events.EventsChannels;
import com.otchi.domain.events.PostCommentedEvent;
import com.otchi.domain.services.PushNotificationsService;
import com.otchi.domain.social.exceptions.PostNotFoundException;
import com.otchi.domain.social.models.Comment;
import com.otchi.domain.social.models.Post;
import com.otchi.domain.social.repositories.PostRepository;
import com.otchi.domain.social.repositories.mocks.MockPostRepository;
import com.otchi.domain.users.models.User;
import com.otchi.utils.DateParser;
import com.otchi.utils.mocks.MockCrudRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class FeedServiceImplTest {

    private PostRepository postRepository = new MockPostRepository();
    private UserService userService = Mockito.mock(UserService.class);
    private DateFactory dateFactory = Mockito.mock(DateFactory.class);
    private PushNotificationsService pushNotificationsService = mock(PushNotificationsService.class);
    private DomainEvents domainEvents = mock(DomainEvents.class);
    private FeedService feedService;
    private User user = new User("email@fofo.com", "firstName_sample", "lastName");

    @Captor
    private ArgumentCaptor<PostCommentedEvent> postCommentedEventArgumentCaptor;

    @Before
    public void setUp() {
        MockCrudRepository.clearDatabase();

        feedService = new FeedServiceImpl(postRepository, userService, pushNotificationsService, dateFactory, domainEvents);

        // Create new Post;
        Post post = new Post(new Date());
        postRepository.save(post);
        //mock user service
        when(userService.findUserByUsername("email@fofo.com")).thenReturn(Optional.of(user));

        MockitoAnnotations.initMocks(this);
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

    @Test
    public void shouldNotifyAuthorThatSomeoneLikedHisPost() {
        String likerUsername = "email@fofo.com";
        feedService.likePost(1L, likerUsername);
        Post post = postRepository.findOne(1L);
        verify(pushNotificationsService).sendLikeNotificationToPostAuthor(post, likerUsername);
    }

    @Test
    public void shouldNotNotifyAuthorIfUserLikedAlreadyHisPost() {
        String likerUsername = "email@fofo.com";
        feedService.likePost(1L, likerUsername);
        feedService.likePost(1L, likerUsername);
        Post post = postRepository.findOne(1L);
        verify(pushNotificationsService, times(1)).sendLikeNotificationToPostAuthor(post, likerUsername);
    }

    @Test(expected = PostNotFoundException.class)
    public void shouldNotAllowToLikeUnExistingPost() {
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
    public void shouldAddCommentToPost() throws Exception {
        String commentContent = "What a delicious meal";
        feedService.commentOnPost(1L, commentContent, "email@fofo.com");
        Post post = postRepository.findOne(1L);
        assertThat(post.getComments()).hasSize(1).extracting(Comment::getContent).containsExactly(commentContent);
        assertThat(post.getComments()).extracting(comment -> comment.getAuthor().getEmail()).containsExactly("email@fofo.com");
    }

    @Test
    public void shouldSetCommentCreationOnDateToNow() throws Exception {
        Date now = DateParser.parse("2015-02-28 12:15:22.8");
        Mockito.when(dateFactory.now()).thenReturn(now);
        feedService.commentOnPost(1L, "What a delicious meal", "email@fofo.com");
        Post post = postRepository.findOne(1L);
        assertThat(post.getComments()).extracting(Comment::getCreatedOn).containsExactly(now);
    }

    @Test(expected = PostNotFoundException.class)
    public void shouldNotAllowToCommentUnExistingPost() {
        feedService.commentOnPost(123L, "comment", "email@fofo.com");
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
    public void shouldNotAllowToDesLikeUnExistingPost() {
        feedService.unlikePost(123L, "email@fofo.com");
    }

    @Test
    public void shouldRaisePostCommentedEvent() throws Exception {
        String commentContent = "What a delicious meal";
        String commentOwner = "email@fofo.com";
        feedService.commentOnPost(1L, commentContent, commentOwner);
        Post post = postRepository.findOne(1L);
        verify(domainEvents).raise(eq(EventsChannels.POST_COMMENTED), postCommentedEventArgumentCaptor.capture());

        PostCommentedEvent postCommentedEvent = postCommentedEventArgumentCaptor.getValue();
        assertThat(postCommentedEvent.getCommentOwner()).isEqualTo(commentOwner);
        assertThat(postCommentedEvent.getPost()).isEqualTo(post);

    }
}