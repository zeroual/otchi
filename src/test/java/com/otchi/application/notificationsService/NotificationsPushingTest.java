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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.springframework.test.util.ReflectionTestUtils.setField;

@Service
public class NotificationsPushingTest {

    private WebsocketMessageSending websocketMessageSending = mock(WebsocketMessageSending.class);
    private NotificationsRepository notificationsRepository = new MockNotificationsRepository();

    private Clock clock = mock(Clock.class);
    private PushNotificationsService pushNotificationsService;
    private Post post;
    private String postAuthor;
    private String likerUsername = "user";

    @Before
    public void setUp() {
        this.post = new Post();
        this.postAuthor = "postAuthor";
        User user = new User(postAuthor, "firstName", "lastName");
        post.setAuthor(user);
        setField(post, "id", 23L);

        MockCrudRepository.clearDatabase();
        pushNotificationsService = new PushNotificationsServiceImpl(websocketMessageSending, notificationsRepository, clock);
    }

    @Test
    public void shouldSendRealTimeNotificationToThePostAuthor() {
        pushNotificationsService.sendLikeNotificationToPostAuthor(post, likerUsername);
        verify(websocketMessageSending).sendLikedEvent(eq(postAuthor), any());
    }

    @Test
    public void shouldSaveNotificationTheLikeNotification() {
        pushNotificationsService.sendLikeNotificationToPostAuthor(post, likerUsername);
        Notification notification = notificationsRepository.findOne(1L);
        assertThat(notification).isNotNull();
        assertThat(notification.getUsername()).isEqualTo(postAuthor);
        assertThat(notification.getType()).isEqualTo(NotificationType.LIKED);
        verify(websocketMessageSending).sendLikedEvent(postAuthor, notification);
    }

    @Test
    public void shouldAssignTheCurrentDateToNotification() throws ParseException {
        LocalDateTime now = LocalDateTime.parse("2015-02-28T12:15:22.8");
        Mockito.when(clock.now()).thenReturn(now);
        Notification notification = pushNotificationsService.sendLikeNotificationToPostAuthor(post, "user");
        assertThat(notification.getId()).isNotNull();
        assertThat(notification.getCreationDate()).isEqualTo(now);
    }

    @Test
    public void shouldSaveTheNotificationSender() {
        Notification notification = pushNotificationsService.sendLikeNotificationToPostAuthor(post, likerUsername);
        assertThat(notification.senderId()).isEqualTo(likerUsername);
    }

    @Test
    public void shouldAssignToNotificationThePostId() {
        Notification notification = pushNotificationsService.sendLikeNotificationToPostAuthor(post, likerUsername);
        assertThat(notification.postId()).isEqualTo(post.getId());
    }
}
