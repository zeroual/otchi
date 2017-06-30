package com.otchi.application.notificationsService;


import com.otchi.application.ForbiddenNotificationUnreadStatusChangingException;
import com.otchi.application.UserService;
import com.otchi.application.impl.NotificationsServiceImpl;
import com.otchi.domain.notifications.models.Notification;
import com.otchi.domain.notifications.models.NotificationsRepository;
import com.otchi.domain.notifications.services.NotificationsService;
import com.otchi.domain.social.repositories.PostRepository;
import com.otchi.domain.social.repositories.mocks.MockNotificationsRepository;
import com.otchi.domain.social.repositories.mocks.MockPostRepository;
import com.otchi.utils.mocks.MockCrudRepository;
import org.junit.Before;
import org.junit.Test;

import static com.otchi.domain.notifications.models.NotificationType.LIKED;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class NotificationsUnreadStateTest {

    private NotificationsRepository notificationsRepository = new MockNotificationsRepository();
    private UserService userService = mock(UserService.class);
    private PostRepository postRepository = new MockPostRepository();
    private NotificationsService notificationsService;
    private Notification notification;
    private String notificationOwner = "user1";

    @Before
    public void setUp() {
        MockCrudRepository.clearDatabase();

        notificationsService = new NotificationsServiceImpl(notificationsRepository, userService, postRepository);
        notification = new Notification(notificationOwner, "user2", 1L, LIKED);
        notificationsRepository.save(asList(notification));
    }

    @Test
    public void shouldMarkNotificationAsRead() {
        assertThat(notification.isUnread()).isTrue();
        Notification notification = notificationsService.markNotificationAsRead(1L, notificationOwner);
        assertThat(notification.isUnread()).isFalse();
    }

    @Test
    public void shouldMarkNotificationAsUnread() {
        notification.markAsRead();
        notificationsRepository.save(notification);

        assertThat(notification.isUnread()).isFalse();
        Notification notification = notificationsService.markNotificationAsUnread(1L, notificationOwner);
        assertThat(notification.isUnread()).isTrue();
    }

    @Test(expected = ForbiddenNotificationUnreadStatusChangingException.class)
    public void shouldNotAllowUserToMarkNotificationAsReadIfIsNotTheOwner() {
        notificationsService.markNotificationAsRead(1L, "user2");
    }

    @Test(expected = ForbiddenNotificationUnreadStatusChangingException.class)
    public void shouldNotAllowUserToMarkNotificationAsUnreadIfIsNotTheOwner() {
        notificationsService.markNotificationAsUnread(1L, "user2");
    }
}