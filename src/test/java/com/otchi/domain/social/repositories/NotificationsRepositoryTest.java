package com.otchi.domain.social.repositories;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.otchi.domain.social.models.Notification;
import com.otchi.utils.AbstractIntegrationTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class NotificationsRepositoryTest extends AbstractIntegrationTest {

    @Autowired
    private NotificationsRepository notificationsRepository;


    @Test
    @DatabaseSetup(value = {"/dbunit/social/notifications.xml"})
    public void shouldMapWithDatabase() {
        Notification notification = notificationsRepository.findOne(1L);
        assertThat(notification).isNotNull();
    }

    @Test
    @DatabaseSetup(value = {"/dbunit/social/notifications.xml"})
    public void shouldFindAllNotificationsByUsername() {
        String username = "user1";
        List<Notification> allByUsername = notificationsRepository.findAllByUsername(username);
        assertThat(allByUsername).hasSize(2);
        assertThat(allByUsername).extracting(Notification::getId).containsExactly(1L, 3L);
    }

    @Test
    @DatabaseSetup(value = {"/dbunit/social/notifications.xml"})
    public void shouldFindAllUnreadNotificationsByUsername() {
        String username = "user1";
        List<Notification> allByUsername = notificationsRepository.findAllByUsernameAndUnreadTrue(username);
        assertThat(allByUsername).hasSize(1);
        assertThat(allByUsername).extracting(Notification::getId).containsExactly(1L);
    }

}
