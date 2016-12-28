package com.otchi.application;

import com.otchi.domain.social.models.Comment;
import com.otchi.domain.social.models.Post;
import com.otchi.domain.social.models.PostContent;
import com.otchi.domain.users.models.User;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;

public class Feed {

    private final Long id;

    private final LocalDateTime createdTime;

    private final User author;

    private final Set<User> likes;

    private final Collection<Comment> comments;

    private final PostContent postContent;

    private final boolean canBeRemoved;

    public Feed(Post post, String username) {
        this.id = post.getId();
        this.createdTime = post.getCreatedTime();
        this.author = post.getAuthor();
        this.likes = post.getLikes();
        this.comments = post.getComments();
        this.postContent = post.getPostContent();
        this.canBeRemoved = post.isOwnedBy(username);
    }


    public Long getId() {
        return id;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public User getAuthor() {
        return author;
    }

    public Set<User> getLikes() {
        return likes;
    }

    public Collection<Comment> getComments() {
        return comments;
    }

    public PostContent getPostContent() {
        return postContent;
    }

    public boolean canBeRemoved() {
        return canBeRemoved;
    }
}
