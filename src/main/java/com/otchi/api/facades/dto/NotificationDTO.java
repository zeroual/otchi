package com.otchi.api.facades.dto;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.otchi.api.facades.serializers.CustomLocalDateTimeSerializer;
import com.otchi.application.NotificationWithSender;
import com.otchi.domain.social.models.NotificationType;

import java.time.LocalDateTime;

public class NotificationDTO {

    private Long id;
    private NotificationType type;
    private Long postId;

    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    private LocalDateTime createdAt;

    private UserDTO sender;

    public NotificationDTO(NotificationWithSender notification) {
        this.id = notification.id();
        this.type = notification.type();
        this.createdAt = notification.creationDate();
        this.postId = notification.postId();
        this.sender = new UserDTO(notification.sender());
    }

    public Long getId() {
        return id;
    }


    public NotificationType getType() {
        return type;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public UserDTO getSender() {
        return sender;
    }

    public Long getPostId() {
        return postId;
    }
}
