package com.otchi.application.impl;

import com.otchi.api.facades.dto.UserDTO;
import com.otchi.application.ForbiddenNotificationUnreadStatusChangingException;
import com.otchi.application.UserService;
import com.otchi.domain.notifications.models.Notification;
import com.otchi.domain.notifications.models.NotificationsRepository;
import com.otchi.domain.notifications.services.NotificationsService;
import com.otchi.domain.notifications.services.WebNotification;
import com.otchi.domain.users.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

@Service
public class NotificationsServiceImpl implements NotificationsService {

    private NotificationsRepository notificationsRepository;
    private UserService userService;

    @Autowired
    public NotificationsServiceImpl(NotificationsRepository notificationsRepository,
                                    UserService userService) {
        this.notificationsRepository = notificationsRepository;
        this.userService = userService;
    }

    @Override
    public List<WebNotification> getAllNotificationsOf(String username) {
        return notificationsRepository.findAllByUsername(username)
                .stream()
                .map(notificationWithSenderTransformer())
                .collect(toList());
    }

    @Override
    public List<WebNotification> getAllUnreadNotificationsOf(String username) {
        return notificationsRepository.findAllByUsernameAndUnreadTrue(username)
                .stream().map(notificationWithSenderTransformer())
                .collect(toList());
    }

    @Override
    public Notification markNotificationAsRead(Long notificationId, String username) {
        Notification notification = notificationsRepository.findOne(notificationId);
        if (notification.canNotChangeUnreadStatusBy(username)) {
            String message = "user with username " + username + " try to mark notification with id " + notificationId + "as read";
            throw new ForbiddenNotificationUnreadStatusChangingException(message);
        }
        notification.markAsRead();
        notificationsRepository.save(notification);
        return notification;
    }

    @Override
    public Notification markNotificationAsUnread(Long notificationId, String username) {
        Notification notification = notificationsRepository.findOne(notificationId);
        if (notification.canNotChangeUnreadStatusBy(username)) {
            String message = "user with username " + username + " try to mark notification with id " + notificationId + "as unread";
            throw new ForbiddenNotificationUnreadStatusChangingException(message);
        }
        notification.markAsUnread();
        notificationsRepository.save(notification);
        return notification;
    }

    private Function<Notification, WebNotification> notificationWithSenderTransformer() {
        return notification -> {
            User sender = getSenderOfThisNotification(notification);
            return new WebNotification(notification, new UserDTO(sender));
        };
    }

    private User getSenderOfThisNotification(Notification notification) {
        return userService.findUserByUsername(notification.senderId()).get();
    }
}
