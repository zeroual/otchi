package com.otchi.domain.notifications.services;

import com.otchi.domain.notifications.models.Notification;

import java.util.List;

public interface NotificationsService {

    List<WebNotification> getAllNotificationsOf(String username);

    List<WebNotification> getAllUnreadNotificationsOf(String username);

    Notification markNotificationAsRead(Long notificationId, String username);

    Notification markNotificationAsUnread(Long notificationId, String username);
}
