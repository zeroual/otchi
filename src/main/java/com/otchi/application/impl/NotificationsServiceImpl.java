package com.otchi.application.impl;

import com.otchi.application.ForbiddenNotificationUnreadStatusChangingException;
import com.otchi.application.NotificationWithSender;
import com.otchi.application.NotificationsService;
import com.otchi.application.UserService;
import com.otchi.domain.social.models.Notification;
import com.otchi.domain.social.repositories.NotificationsRepository;
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
    public List<NotificationWithSender> getAllNotificationsOf(String username) {
        return notificationsRepository.findAllByUsername(username)
                .stream()
                .map(notificationWithSenderTransformer())
                .collect(toList());
    }

    @Override
    public List<NotificationWithSender> getAllUnreadNotificationsOf(String username) {
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

    private Function<Notification, NotificationWithSender> notificationWithSenderTransformer() {
        return notification -> {
            User sender = getSenderOfThisNotification(notification);
            return new NotificationWithSender(notification, sender);
        };
    }

    private User getSenderOfThisNotification(Notification notification) {
        return userService.findUserByUsername(notification.senderId()).get();
    }
}
