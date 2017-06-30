package com.otchi.application.notificationsService;


import com.otchi.api.facades.dto.UserDTO;
import com.otchi.application.UserService;
import com.otchi.application.impl.NotificationsServiceImpl;
import com.otchi.domain.kitchen.Recipe;
import com.otchi.domain.notifications.models.Notification;
import com.otchi.domain.notifications.models.NotificationsRepository;
import com.otchi.domain.notifications.services.NotificationsService;
import com.otchi.domain.notifications.services.WebNotification;
import com.otchi.domain.social.models.Post;
import com.otchi.domain.social.models.Story;
import com.otchi.domain.social.repositories.PostRepository;
import com.otchi.domain.social.repositories.mocks.MockNotificationsRepository;
import com.otchi.domain.social.repositories.mocks.MockPostRepository;
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
    private PostRepository postRepository = new MockPostRepository();
    private NotificationsService notificationsService;
    private LocalDateTime notificationsCreationDate;
    private Notification notification1, notification2, notification3;
    private User user1, user2;
    private Post post1, post2;
    private Recipe recipe;
    private Story story;

    @Before
    public void setUp() {
        MockCrudRepository.clearDatabase();

        String userName1 = "user1", userName2 = "user2";

        notificationsService = new NotificationsServiceImpl(
                notificationsRepository, userService, postRepository);
        user1 = asUser().withUsername(userName1)
                .withFirstName("firstName_1")
                .withLastName("lastName_1")
                .build();
        Optional<User> expectedUser1 = Optional.of(user1);
        when(userService.findUserByUsername(userName1)).thenReturn(expectedUser1);

        user2 = asUser().withUsername(userName2)
                .withFirstName("firstName_2")
                .withLastName("lastName_2")
                .build();
        Optional<User> expectedUser2 = Optional.of(user2);
        when(userService.findUserByUsername(userName2)).thenReturn(expectedUser2);

        recipe = new Recipe();
        recipe.setTitle("Recipe title");
        post1 = new Post();
        post1.setPostContent(recipe);

        postRepository.save(post1);


        story = new Story("Story content");
        post2 = new Post();
        post2.setPostContent(story);
        postRepository.save(post2);

        notificationsCreationDate = LocalDateTime.now();
        notification1 = new Notification(userName1, userName2, post1.getId(), LIKED);
        notification2 = new Notification(userName2, userName1, post2.getId(), LIKED);
        notification3 = new Notification(userName1, userName2, post1.getId(), LIKED);

        notification1.changeCreationDateTo(notificationsCreationDate);
        notification2.changeCreationDateTo(notificationsCreationDate);
        notification3.changeCreationDateTo(notificationsCreationDate);

        notification3.markAsRead();
        notificationsRepository.save(asList(notification1, notification2, notification3));
    }

    @Test
    public void shouldGetAllNotificationsForUser() {

        List<WebNotification> notificationsOfUser1 = notificationsService.getAllNotificationsOf(user1.getUsername());
        assertThat(notificationsOfUser1).hasSize(2);
        assertThat(notificationsOfUser1).extracting(WebNotification::getId).containsOnly(1L, 3L);
        assertThat(notificationsOfUser1).extracting(WebNotification::getType).containsOnly(LIKED, LIKED);
        assertThat(notificationsOfUser1).extracting(WebNotification::getCreatedAt)
                .containsOnly(notificationsCreationDate, notificationsCreationDate);
        assertThat(notificationsOfUser1).extracting(WebNotification::getSender)
                .containsOnly(new UserDTO(user2), new UserDTO(user2));
        assertThat(notificationsOfUser1).extracting(WebNotification::getPostId).containsOnly(post1.getId(), post1.getId());
    }

    @Test
    public void shouldReturnUnreadNotifications() {

        List<WebNotification> notificationsOfUser1 = notificationsService.getAllUnreadNotificationsOf(user1.getUsername());
        assertThat(notificationsOfUser1).hasSize(1);
        assertThat(notificationsOfUser1).extracting(WebNotification::getId).containsExactly(1L);
        assertThat(notificationsOfUser1).extracting(WebNotification::getType).containsOnly(LIKED);
        assertThat(notificationsOfUser1).extracting(WebNotification::getCreatedAt).containsOnly(notificationsCreationDate);
        assertThat(notificationsOfUser1).extracting(WebNotification::getSender).containsOnly(new UserDTO(user2));
        assertThat(notificationsOfUser1).extracting(WebNotification::getPostId).containsOnly(post1.getId(), post1.getId());
    }

    @Test
    public void notificationPreviewShouldBeRecipeTitle(){
        List<WebNotification> notificationsOfUser1 = notificationsService.getAllNotificationsOf(user1.getUsername());
        assertThat(notificationsOfUser1).hasSize(2);
        assertThat(notificationsOfUser1).extracting(WebNotification::getPostPreview).containsExactly(recipe.getTitle(), recipe.getTitle());
    }

    @Test
    public void notificationPreviewShouldBeStoryStoryContent(){
      List<WebNotification> notificationsOfUser1 = notificationsService.getAllNotificationsOf(user2.getUsername());
      assertThat(notificationsOfUser1).hasSize(1);
      assertThat(notificationsOfUser1).extracting(WebNotification::getPostPreview).containsExactly(story.content());
    }
}