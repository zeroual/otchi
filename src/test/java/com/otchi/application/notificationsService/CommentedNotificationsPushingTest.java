package com.otchi.application.notificationsService;

import com.otchi.application.utils.Clock;
import com.otchi.domain.services.PushNotificationsService;
import com.otchi.domain.services.PushNotificationsServiceImpl;
import com.otchi.domain.social.models.Notification;
import com.otchi.domain.social.models.NotificationType;
import com.otchi.domain.social.models.Post;
import com.otchi.domain.social.repositories.NotificationsRepository;
import com.otchi.domain.social.repositories.mocks.MockNotificationsRepository;
import com.otchi.domain.users.models.User;
import com.otchi.infrastructure.notifications.WebsocketMessageSending;
import com.otchi.utils.mocks.MockCrudRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.LocalDateTime;

import static com.otchi.domain.users.models.UserBuilder.asUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

@Service
public class CommentedNotificationsPushingTest {

    private WebsocketMessageSending websocketMessageSending = mock(WebsocketMessageSending.class);
    private NotificationsRepository notificationsRepository = new MockNotificationsRepository();

    private Clock clock = mock(Clock.class);
    private PushNotificationsService pushNotificationsService;
    private Post post;
    private String postAuthor = "user";
    private String commentOwner = "commentOwner";

    @Before
    public void setUp() {
        this.post = new Post();
        this.postAuthor = "postAuthor";
        User author = asUser().withUsername(postAuthor)
                .withFirstName("firstName")
                .withLastName("lastName")
                .build();
        post.setAuthor(author);

        MockCrudRepository.clearDatabase();
        pushNotificationsService = new PushNotificationsServiceImpl(websocketMessageSending, notificationsRepository, clock);
    }

    @Test
    public void shouldSaveNotificationCommentedNotification() {
        pushNotificationsService.sendCommentedNotificationToPostAuthor(post, commentOwner);
        Notification notification = notificationsRepository.findOne(1L);
        assertThat(notification).isNotNull();
        assertThat(notification.getUsername()).isEqualTo(postAuthor);
        assertThat(notification.senderId()).isEqualTo(commentOwner);
        assertThat(notification.getType()).isEqualTo(NotificationType.COMMENT_ON_POST);
    }

    @Test
    public void shouldAssignTheCurrentDateToNotification() throws ParseException {
        LocalDateTime now = LocalDateTime.parse("2015-02-28T12:15:22.8");
        Mockito.when(clock.now()).thenReturn(now);
        Notification notification = pushNotificationsService.sendCommentedNotificationToPostAuthor(post, commentOwner);
        assertThat(notification.getId()).isNotNull();
        assertThat(notification.getCreationDate()).isEqualTo(now);
    }

    @Test
    public void shouldSaveTheNotificationSender() {
        Notification notification = pushNotificationsService.sendLikeNotificationToPostAuthor(post, commentOwner);
        assertThat(notification.senderId()).isEqualTo(commentOwner);
    }

    @Test
    public void shouldAssignToNotificationThePostId() {
        Notification notification = pushNotificationsService.sendCommentedNotificationToPostAuthor(post, commentOwner);
        assertThat(notification.postId()).isEqualTo(post.getId());
    }

    @Test
    public void shouldSendRealTimeNotificationToThePostAuthor() {
        Notification notification = pushNotificationsService.sendCommentedNotificationToPostAuthor(post, commentOwner);
        verify(websocketMessageSending).sendNotification(eq(notification));
    }

    @Test
    public void shouldNotSendNotificationToThePostAuthorIfHeReplyToComment() {
        Notification notification = pushNotificationsService.sendCommentedNotificationToPostAuthor(post, postAuthor);
        verify(websocketMessageSending, times(0)).sendNotification(eq(notification));

    }

}
