package com.otchi.domain.social.repositories.mocks;

import com.otchi.domain.social.models.Notification;
import com.otchi.domain.social.repositories.NotificationsRepository;
import com.otchi.utils.mocks.MockCrudRepository;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class MockNotificationsRepository extends MockCrudRepository<Notification, Long> implements NotificationsRepository {
    public MockNotificationsRepository() {
        super(Notification.class);
    }

    @Override
    public List<Notification> findAllByUsername(String username) {
        return this.findAll(notification -> notification.getUsername().equals(username)).stream().collect(toList());
    }

    @Override
    public List<Notification> findAllByUsernameAndUnreadTrue(String username) {
        return this.findAllByUsername(username)
                .stream()
                .filter(notification -> notification.isUnread()).collect(toList());
    }
}
