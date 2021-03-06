package com.otchi.application;

import com.otchi.application.impl.FeedServiceImpl;
import com.otchi.application.utils.Clock;
import com.otchi.domain.notifications.events.DomainEvents;
import com.otchi.domain.notifications.events.LikePostEvent;
import com.otchi.domain.notifications.events.PostCommentedEvent;
import com.otchi.domain.social.events.PostDeletedEvent;
import com.otchi.domain.social.exceptions.PostNotFoundException;
import com.otchi.domain.social.exceptions.ResourceNotAuthorizedException;
import com.otchi.domain.social.models.Comment;
import com.otchi.domain.social.models.Post;
import com.otchi.domain.social.repositories.PostRepository;
import com.otchi.domain.social.repositories.mocks.MockPostRepository;
import com.otchi.domain.users.models.User;
import com.otchi.utils.mocks.MockCrudRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.otchi.domain.users.models.UserBuilder.asUser;
import static java.time.LocalDateTime.now;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class FeedServiceImplTest {

    private PostRepository postRepository = new MockPostRepository();
    private UserService userService = Mockito.mock(UserService.class);
    private Clock clock = Mockito.mock(Clock.class);
    private DomainEvents domainEvents = mock(DomainEvents.class);
    private FeedService feedService;


    @Captor
    private ArgumentCaptor<PostCommentedEvent> postCommentedEventArgumentCaptor;

    @Captor
    private ArgumentCaptor<LikePostEvent> likePostEventArgumentCaptor;


    @Captor
    private ArgumentCaptor<PostDeletedEvent> postDeletedEventArgumentCaptor;


    @Before
    public void setUp() {
        MockCrudRepository.clearDatabase();

        feedService = new FeedServiceImpl(postRepository, userService, clock, domainEvents);

        // Create new Post1;
        User user1 =
                asUser().withUsername("user1@fofo.com")
                        .withFirstName("FirstName1")
                        .withLastName("LastName1")
                        .build();
        Post post1 = new Post(now());
        post1.setAuthor(user1);
        postRepository.save(post1);

        //Create new Post2;
        User user2 = asUser().withUsername("user2@fofo.com")
                .withFirstName("FirstName2")
                .withLastName("LastName2")
                .build();
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
        assertThat(post.getComments()).extracting(comment -> comment.getAuthor().getUsername()).containsExactly("user2@fofo.com");
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
    public void shouldDeletePostFromDataBase() {
        feedService.deletePost(1L, "user1@fofo.com");
        assertThat(postRepository.findOne(1L)).isNull();
    }

    @Test(expected = ResourceNotAuthorizedException.class)
    public void shouldNotDeletePostIfUserIsNotOwner() {
        feedService.deletePost(1L, "removePostViewsCount");
    }

    @Test
    public void shouldRaiseEventWhenPostIsRemoved() {
        feedService.deletePost(1L, "user1@fofo.com");
        verify(domainEvents).raise(postDeletedEventArgumentCaptor.capture());
        PostDeletedEvent postDeletedEvent = postDeletedEventArgumentCaptor.getValue();
        assertThat(postDeletedEvent.getPostId()).isEqualTo(1);

    }
}
