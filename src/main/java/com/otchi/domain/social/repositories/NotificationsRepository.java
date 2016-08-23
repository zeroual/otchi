package com.otchi.domain.social.repositories;


import com.otchi.domain.social.models.Notification;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface NotificationsRepository extends CrudRepository<Notification, Long> {

    List<Notification> findAllByUsername(String username);

    List<Notification> findAllByUsernameAndUnreadTrue(String username);
}
