package com.otchi.domain.notifications.services;

import com.otchi.api.facades.dto.UserDTO;
import com.otchi.domain.notifications.models.Notification;
import com.otchi.domain.notifications.models.NotificationType;

import java.time.LocalDateTime;
import java.util.Objects;

public class WebNotification {
    private Long id;
    private String to;
    private UserDTO sender;
    private NotificationType type;
    private LocalDateTime createdAt;
    private Long postId;

    private String postPreview;

    public WebNotification(Notification notification, UserDTO sender, String postPreview) {
        this.id = notification.getId();
        this.to = notification.getUsername();
        this.type = notification.getType();
        this.createdAt = notification.getCreationDate();
        this.postId = notification.postId();
        this.sender = sender;
        this.postPreview = postPreview;
    }

    public WebNotification() {

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public UserDTO getSender() {
        return sender;
    }

    public void setSender(UserDTO sender) {
        this.sender = sender;
    }

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getPostPreview() { return postPreview; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WebNotification that = (WebNotification) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(to, that.to) &&
                Objects.equals(sender, that.sender) &&
                type == that.type &&
                Objects.equals(createdAt, that.createdAt) &&
                Objects.equals(postId, that.postId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, to, sender, type, createdAt, postId);
    }
}
