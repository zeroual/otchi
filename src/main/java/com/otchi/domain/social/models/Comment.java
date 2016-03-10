package com.otchi.domain.social.models;

import com.otchi.domain.users.models.User;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "POST_COMMENT")
public class Comment {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "AUTHOR_ID")
    private User author;

    @Column(name = "CONTENT")
    private String content;

    @Column(name = "CREATED_ON")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;


    private Comment(){}

    public Comment(User author, String content,Date createdOn) {
        this.author  = author;
        this.content = content;
        this.createdOn = createdOn;
    }

    public Long getId() {
        return id;
    }

    public User getAuthor() {
        return author;
    }


    public String getContent() {
        return content;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

}
