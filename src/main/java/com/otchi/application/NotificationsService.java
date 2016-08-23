package com.otchi.application;

import java.util.List;

public interface NotificationsService {

    List<NotificationWithSender> getAllNotificationsOf(String username);

    List<NotificationWithSender> getAllUnreadNotificationsOf(String username);

}
