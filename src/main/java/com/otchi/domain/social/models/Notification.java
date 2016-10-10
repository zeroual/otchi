package com.otchi.domain.social.models;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "NOTIFICATION")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "TYPE")
    @Enumerated(EnumType.STRING)
    private NotificationType type;

    @Column(name = "UNREAD")
    private boolean unread = true;

    @Column(name = "CREATED_AT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    @Column(name = "SENDER_ID")
    private String senderId;

    @Column(name = "POST_ID")
    private Long postId;

    private Notification() {
    }

    public Notification(String username, String senderId, Long postId, NotificationType type) {
        this.username = username;
        this.senderId = senderId;
        this.postId = postId;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public NotificationType getType() {
        return type;
    }

    public void markAsRead() {
        this.unread = false;
    }

    public void markAsUnread() {
        this.unread = true;
    }

    public boolean isUnread() {
        return unread;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void changeCreationDateTo(Date newCreationDate) {
        this.creationDate = newCreationDate;
    }

    public String senderId() {
        return this.senderId;
    }


    public Long postId() {
        return postId;
    }

    public boolean canNotChangeUnreadStatusBy(String username) {
        return !this.username.equals(username);
    }

}
