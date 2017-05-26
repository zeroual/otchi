package com.otchi.application.notificationsService;


import com.otchi.api.facades.dto.UserDTO;
import com.otchi.application.UserService;
import com.otchi.application.impl.NotificationsServiceImpl;
import com.otchi.domain.notifications.models.Notification;
import com.otchi.domain.notifications.models.NotificationsRepository;
import com.otchi.domain.notifications.services.NotificationsService;
import com.otchi.domain.notifications.services.WebNotification;
import com.otchi.domain.social.repositories.mocks.MockNotificationsRepository;
import com.otchi.domain.users.models.User;
import com.otchi.utils.mocks.MockCrudRepository;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.otchi.domain.notifications.models.NotificationType.LIKED;
import static com.otchi.domain.users.models.UserBuilder.asUser;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class NotificationsFetchingTest {

    private NotificationsRepository notificationsRepository = new MockNotificationsRepository();
    private UserService userService = mock(UserService.class);
    private NotificationsService notificationsService;
    private LocalDateTime notificationsCreationDate;
    private Notification notification1, notification2, notification3;
    private User notificationSender;
    private Long postId = 12L;

    @Before
    public void setUp() {
        MockCrudRepository.clearDatabase();

        notificationsService = new NotificationsServiceImpl(
                notificationsRepository, userService);
        notificationSender = asUser().withUsername("user2")
                .withFirstName("firstName")
                .withLastName("lastName")
                .build();
        Optional<User> expectedUser = Optional.of(notificationSender);
        when(userService.findUserByUsername("user2")).thenReturn(expectedUser);

        notificationsCreationDate = LocalDateTime.now();
        notification1 = new Notification("user1", "user2", postId, LIKED);
        notification2 = new Notification("user2", "user1", postId, LIKED);
        notification3 = new Notification("user1", "user2", postId, LIKED);

        notification1.changeCreationDateTo(notificationsCreationDate);
        notification2.changeCreationDateTo(notificationsCreationDate);
        notification3.changeCreationDateTo(notificationsCreationDate);

        notification3.markAsRead();
        notificationsRepository.save(asList(notification1, notification2, notification3));
    }

    @Test
    public void shouldGetAllNotificationsForUser() {

        List<WebNotification> notificationsOfUser1 = notificationsService.getAllNotificationsOf("user1");
        assertThat(notificationsOfUser1).hasSize(2);
        assertThat(notificationsOfUser1).extracting(WebNotification::getId).containsOnly(1L, 3L);
        assertThat(notificationsOfUser1).extracting(WebNotification::getType).containsOnly(LIKED, LIKED);
        assertThat(notificationsOfUser1).extracting(WebNotification::getCreatedAt)
                .containsOnly(notificationsCreationDate, notificationsCreationDate);
        assertThat(notificationsOfUser1).extracting(WebNotification::getSender)
                .containsOnly(new UserDTO(notificationSender), new UserDTO(notificationSender));
        assertThat(notificationsOfUser1).extracting(WebNotification::getPostId).containsOnly(postId, postId);
    }

    @Test
    public void shouldReturnUnreadNotifications() {

        List<WebNotification> notificationsOfUser1 = notificationsService.getAllUnreadNotificationsOf("user1");
        assertThat(notificationsOfUser1).hasSize(1);
        assertThat(notificationsOfUser1).extracting(WebNotification::getId).containsExactly(1L);
        assertThat(notificationsOfUser1).extracting(WebNotification::getType).containsOnly(LIKED);
        assertThat(notificationsOfUser1).extracting(WebNotification::getCreatedAt).containsOnly(notificationsCreationDate);
        assertThat(notificationsOfUser1).extracting(WebNotification::getSender).containsOnly(new UserDTO(notificationSender));
        assertThat(notificationsOfUser1).extracting(WebNotification::getPostId).containsOnly(postId, postId);
    }
}