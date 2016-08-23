package com.otchi.api.facades.dto;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.otchi.api.facades.serializers.CustomDateSerializer;
import com.otchi.application.NotificationWithSender;
import com.otchi.domain.social.models.NotificationType;

import java.util.Date;

public class NotificationDTO {

    private Long id;
    private NotificationType type;
    private Long postId;

    @JsonSerialize(using = CustomDateSerializer.class)
    private Date createdAt;

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

    public Date getCreatedAt() {
        return createdAt;
    }

    public UserDTO getSender() {
        return sender;
    }

    public Long getPostId() {
        return postId;
    }
}
