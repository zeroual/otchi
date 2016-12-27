package com.otchi.application;

import com.otchi.application.impl.FeedServiceImpl;
import com.otchi.application.utils.DateFactory;
import com.otchi.domain.events.DomainEvents;
import com.otchi.domain.events.LikePostEvent;
import com.otchi.domain.events.PostCommentedEvent;
import com.otchi.domain.social.exceptions.PostNotFoundException;
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

import static java.time.LocalDateTime.now;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class FeedServiceImplTest {

    private PostRepository postRepository = new MockPostRepository();
    private UserService userService = Mockito.mock(UserService.class);
    private DateFactory dateFactory = Mockito.mock(DateFactory.class);
    private DomainEvents domainEvents = mock(DomainEvents.class);
    private FeedService feedService;
    private User user = new User("email@fofo.com", "firstName_sample", "lastName");

    @Captor
    private ArgumentCaptor<PostCommentedEvent> postCommentedEventArgumentCaptor;

    @Captor
    private ArgumentCaptor<LikePostEvent> likePostEventArgumentCaptor;

    @Before
    public void setUp() {
        MockCrudRepository.clearDatabase();

        feedService = new FeedServiceImpl(postRepository, userService, dateFactory, domainEvents);

        // Create new Post;
        Post post = new Post(now());
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
    public void shouldRaiseLikePostEventIfSomeoneLikedHisPost() {
        String likerUsername = "email@fofo.com";
        feedService.likePost(1L, likerUsername);
        Post post = postRepository.findOne(1L);
        verify(domainEvents).raise(likePostEventArgumentCaptor.capture());
        LikePostEvent likePostEvent = likePostEventArgumentCaptor.getValue();
        assertThat(likePostEvent.getLikedPost()).isEqualTo(post);
        assertThat(likePostEvent.getLikeOwner()).isEqualTo(likerUsername);
    }

    @Test
    public void shouldNotRaiseLikePostEventIfUserLikedAlreadyHisPost() {
        String likerUsername = "email@fofo.com";
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
        LocalDateTime now = LocalDateTime.parse("2015-02-28T12:15:22.8");
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
        verify(domainEvents).raise(postCommentedEventArgumentCaptor.capture());

        PostCommentedEvent postCommentedEvent = postCommentedEventArgumentCaptor.getValue();
        assertThat(postCommentedEvent.getCommentOwner()).isEqualTo(commentOwner);
        assertThat(postCommentedEvent.getPost()).isEqualTo(post);

    }
}