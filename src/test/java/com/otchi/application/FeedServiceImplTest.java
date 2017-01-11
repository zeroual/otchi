package com.otchi.application;

import com.otchi.application.impl.FeedServiceImpl;
import com.otchi.application.utils.Clock;
import com.otchi.domain.events.DomainEvents;
import com.otchi.domain.events.impl.LikePostEvent;
import com.otchi.domain.events.impl.PostCommentedEvent;
import com.otchi.domain.services.PushNotificationsService;
import com.otchi.domain.social.exceptions.PostNotFoundException;
import com.otchi.domain.social.models.Comment;
import com.otchi.domain.social.models.Post;
import com.otchi.domain.social.repositories.PostRepository;
import com.otchi.domain.social.repositories.mocks.MockPostRepository;
import com.otchi.domain.users.models.User;
import com.otchi.utils.mocks.MockCrudRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import java.time.LocalDateTime;
import java.util.Optional;

import static java.time.LocalDateTime.now;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class FeedServiceImplTest {

    private PostRepository postRepository = new MockPostRepository();
    private UserService userService = Mockito.mock(UserService.class);
    private Clock clock = Mockito.mock(Clock.class);
    private PushNotificationsService pushNotificationsService = mock(PushNotificationsService.class);
    private DomainEvents domainEvents = mock(DomainEvents.class);
    private FeedService feedService;


    @Captor
    private ArgumentCaptor<PostCommentedEvent> postCommentedEventArgumentCaptor;

    @Captor
    private ArgumentCaptor<LikePostEvent> likePostEventArgumentCaptor;

    @Before
    public void setUp() {
        MockCrudRepository.clearDatabase();

        feedService = new FeedServiceImpl(postRepository, userService, clock, domainEvents);

        // Create new Post1;
        User user1 = new User("user1@fofo.com", "FirstName1", "LastName1");
        Post post1 = new Post(now());
        post1.setAuthor(user1);
        postRepository.save(post1);

        //Create new Post2;
        User user2 = new User("user2@fofo.com", "FirstName2", "LastName2");
        Post post2 = new Post(now());
        post2.setAuthor(user2);
        postRepository.save(post2);

        //mock user service
        when(userService.findUserByUsername("user1@fofo.com")).thenReturn(Optional.of(user1));
        when(userService.findUserByUsername("user2@fofo.com")).thenReturn(Optional.of(user2));

        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldAddNewLikeToPost() throws Exception {
        feedService.likePost(1L, "user2@fofo.com");
        Post post = postRepository.findOne(1L);
        assertThat(post.getLikes()).hasSize(1);
        assertThat(post.getLikes())
                .extracting(user -> user.getFirstName())
                .containsExactly("FirstName2");
    }

    @Test
    public void shouldRaiseLikePostEventIfSomeoneLikedHisPost() {
        String likerUsername = "user2@fofo.com";
        feedService.likePost(1L, likerUsername);
        Post post = postRepository.findOne(1L);
        verify(domainEvents).raise(likePostEventArgumentCaptor.capture());
        LikePostEvent likePostEvent = likePostEventArgumentCaptor.getValue();
        assertThat(likePostEvent.getLikedPost()).isEqualTo(post);
        assertThat(likePostEvent.getLikeOwner()).isEqualTo(likerUsername);
    }

    @Test
    public void shouldNotRaiseLikePostEventIfUserLikedAlreadyHisPost() {
        String likerUsername = "user2@fofo.com";
        feedService.likePost(1L, likerUsername);
        feedService.likePost(1L, likerUsername);
        Post post = postRepository.findOne(1L);
        verify(domainEvents, times(1)).raise(likePostEventArgumentCaptor.capture());
        LikePostEvent likePostEvent = likePostEventArgumentCaptor.getValue();
        assertThat(likePostEvent.getLikeOwner()).isEqualTo(likerUsername);
        assertThat(likePostEvent.getLikedPost()).isEqualTo(post);
    }

    @Test(expected = PostNotFoundException.class)
    public void shouldNotAllowToLikeUnExistingPost() {
        feedService.likePost(123L, "user2@fofo.com");
    }

    @Test
    public void shouldNotAddLikeToPostIfAlreadyLiked() throws Exception {
        feedService.likePost(1L, "user2@fofo.com");
        Post post = postRepository.findOne(1L);
        feedService.likePost(1L, "user2@fofo.com");
        assertThat(post.getLikes()).hasSize(1);
    }

    @Test
    public void shouldAddCommentToPost() throws Exception {
        String commentContent = "What a delicious meal";
        feedService.commentOnPost(1L, commentContent, "user2@fofo.com");
        Post post = postRepository.findOne(1L);
        assertThat(post.getComments()).hasSize(1).extracting(Comment::getContent).containsExactly(commentContent);
        assertThat(post.getComments()).extracting(comment -> comment.getAuthor().getEmail()).containsExactly("user2@fofo.com");
    }

    @Test
    public void shouldSetCommentCreationOnDateToNow() throws Exception {
        LocalDateTime now = LocalDateTime.parse("2015-02-28T12:15:22.8");
        Mockito.when(clock.now()).thenReturn(now);
        feedService.commentOnPost(1L, "What a delicious meal", "user2@fofo.com");
        Post post = postRepository.findOne(1L);
        assertThat(post.getComments()).extracting(Comment::getCreatedOn).containsExactly(now);
    }

    @Test(expected = PostNotFoundException.class)
    public void shouldNotAllowToCommentUnExistingPost() {
        feedService.commentOnPost(123L, "comment", "user2@fofo.com");
    }

    @Test
    public void shouldUnlikePostIfAlreadyLiked() throws Exception {
        Post post = postRepository.findOne(1L);
        feedService.likePost(1L, "user2@fofo.com");
        assertThat(post.getLikes()).hasSize(1);
        feedService.unlikePost(1L, "user2@fofo.com");
        assertThat(post.getLikes()).hasSize(0);
    }

    @Test(expected = PostNotFoundException.class)
    public void shouldNotAllowToDesLikeUnExistingPost() {
        feedService.unlikePost(123L, "user2@fofo.com");
    }

    @Test
    public void shouldRaisePostCommentedEvent() throws Exception {
        String commentContent = "What a delicious meal";
        String commentOwner = "user2@fofo.com";
        feedService.commentOnPost(1L, commentContent, commentOwner);
        Post post = postRepository.findOne(1L);
        verify(domainEvents).raise(postCommentedEventArgumentCaptor.capture());

        PostCommentedEvent postCommentedEvent = postCommentedEventArgumentCaptor.getValue();
        assertThat(postCommentedEvent.getCommentOwner()).isEqualTo(commentOwner);
        assertThat(postCommentedEvent.getPost()).isEqualTo(post);

    }

    @Test
    public void shouldNotSendNotificationWhenUserLikesHisPost() {
        String likerUserName = "user1@fofo.com";
        feedService.likePost(1L, likerUserName);
        Post post = postRepository.findOne(1L);
        verify(pushNotificationsService, never()).sendLikeNotificationToPostAuthor(post, likerUserName);
        assertThat(post.getLikes()).hasSize(1);
    }

    @Test
    public void shouldNotSendNotificationWhenUserCommentsHisPost() {
        String commentContent = "What a delicious meal";
        String commentOwner = "user1@fofo.com";
        feedService.commentOnPost(1L, commentContent, commentOwner);
        Post post = postRepository.findOne(1L);
        verify(pushNotificationsService, never()).sendCommentedNotificationToPostAuthor(post, commentOwner);
        assertThat(post.getComments()).hasSize(1);
    }
}