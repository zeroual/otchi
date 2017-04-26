package com.otchi.application;

import com.otchi.domain.social.models.Notification;
import com.otchi.domain.social.models.NotificationType;
import com.otchi.domain.users.models.User;

import java.time.LocalDateTime;

public class NotificationWithSender {

    private final User sender;
    private Long id;
    private NotificationType type;
    private LocalDateTime creationDate;
    private Long postId;

    public NotificationWithSender(Notification notification, User sender) {
        this.id = notification.getId();
        this.type = notification.getType();
        this.creationDate = notification.getCreationDate();
        this.postId = notification.postId();
        this.sender = sender;
    }

    public Long id() {
        return id;
    }

    public NotificationType type() {
        return type;
    }

    public LocalDateTime creationDate() {
        return creationDate;
    }

    public User sender() {
        return this.sender;
    }

    public Long postId() {
        return postId;
    }
}
