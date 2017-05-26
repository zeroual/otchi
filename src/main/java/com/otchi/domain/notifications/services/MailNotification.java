package com.otchi.domain.notifications.services;

import com.otchi.domain.notifications.models.NotificationType;
import com.otchi.domain.users.models.User;

import java.util.Objects;

public class MailNotification {
    private User to;
    private User from;
    private NotificationType type;
    private Long postId;

    public MailNotification() {
    }


    public User getTo() {
        return to;
    }

    public void setTo(User to) {
        this.to = to;
    }

    public User getFrom() {
        return from;
    }

    public void setFrom(User from) {
        this.from = from;
    }

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MailNotification that = (MailNotification) o;
        return Objects.equals(to, that.to) &&
                Objects.equals(from, that.from) &&
                type == that.type &&
                Objects.equals(postId, that.postId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(to, from, type, postId);
    }
}
