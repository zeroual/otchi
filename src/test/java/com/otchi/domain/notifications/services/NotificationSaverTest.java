package com.otchi.domain.notifications.services;

import api.stepsDefinition.MocakableClock;
import com.otchi.application.utils.Clock;
import com.otchi.domain.notifications.events.LikePostEvent;
import com.otchi.domain.notifications.events.PostCommentedEvent;
import com.otchi.domain.notifications.models.Notification;
import com.otchi.domain.notifications.models.NotificationsRepository;
import com.otchi.domain.social.models.Post;
import com.otchi.domain.social.repositories.mocks.MockNotificationsRepository;
import com.otchi.domain.users.models.User;
import com.otchi.utils.mocks.MockCrudRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;

import static com.otchi.domain.notifications.models.NotificationType.COMMENT_ON_POST;
import static com.otchi.domain.notifications.models.NotificationType.LIKED;
import static java.time.LocalDateTime.now;
import static java.time.LocalDateTime.parse;
import static java.time.format.DateTimeFormatter.ofPattern;
import static org.assertj.core.api.Assertions.assertThat;

public class NotificationSaverTest {

    private NotificationsRepository notificationsRepository = new MockNotificationsRepository();
    private LocalDateTime now = parse("2017-07-13 06:48:21", ofPattern("yyyy-MM-dd HH:mm:ss"));
    private Clock clock = new MocakableClock(now);
    private NotificationSaver notificationSaver = new NotificationSaver(notificationsRepository, clock);

    @Before
    public void setUp() {
        MockCrudRepository.clearDatabase();
    }

    @Test
    public void shouldCreateAndSaveNotificationFromLikeEvent() {
        Post post = new Post(now());
        ReflectionTestUtils.setField(post, "id", 23L);
        String postOwner = "postOwner";
        post.setAuthor(new User(postOwner));

        String likeOwner = "likeOwner";
        LikePostEvent postLikedEvent = new LikePostEvent(post, likeOwner);

        Notification notification = notificationSaver.createLikeNotificationFrom(postLikedEvent);
        assertThat(notificationsRepository.count()).isEqualTo(1);

        assertThat(notification.getType()).isEqualTo(LIKED);
        assertThat(notification.getUsername()).isEqualTo(postOwner);
        assertThat(notification.getCreationDate()).isEqualTo(now);
        assertThat(notification.postId()).isEqualTo(23L);
        assertThat(notification.isUnread()).isTrue();
        assertThat(notification.senderId()).isEqualTo(likeOwner);

    }

    @Test
    public void shouldCreateAndSaveNotificationFromCommentedEvent() {
        Post post = new Post(now());
        ReflectionTestUtils.setField(post, "id", 23L);
        String postOwner = "postOwner";
        post.setAuthor(new User(postOwner));

        String commenter = "commenter";
        PostCommentedEvent postCommentedEvent = new PostCommentedEvent(post, commenter);

        Notification notification = notificationSaver.createCommentedNotificationFrom(postCommentedEvent);
        assertThat(notificationsRepository.count()).isEqualTo(1);

        assertThat(notification.getType()).isEqualTo(COMMENT_ON_POST);
        assertThat(notification.getUsername()).isEqualTo(postOwner);
        assertThat(notification.getCreationDate()).isEqualTo(now);
        assertThat(notification.postId()).isEqualTo(23L);
        assertThat(notification.isUnread()).isTrue();
        assertThat(notification.senderId()).isEqualTo(commenter);

    }

}