package com.otchi.application;

import com.otchi.domain.social.models.Notification;

import java.util.List;

public interface NotificationsService {

    List<NotificationWithSender> getAllNotificationsOf(String username);

    List<NotificationWithSender> getAllUnreadNotificationsOf(String username);

    Notification markNotificationAsRead(Long notificationId, String username);

    Notification markNotificationAsUnread(Long notificationId, String username);
}
