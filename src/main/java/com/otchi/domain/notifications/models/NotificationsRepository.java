package com.otchi.domain.notifications.models;


import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface NotificationsRepository extends CrudRepository<Notification, Long> {

    List<Notification> findAllByUsername(String username);

    List<Notification> findAllByUsernameAndUnreadTrue(String username);
}
