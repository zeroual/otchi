package com.otchi.application;

import com.otchi.domain.social.models.Comment;
import com.otchi.domain.social.models.Post;
import com.otchi.domain.social.models.PostContent;
import com.otchi.domain.users.models.User;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class Feed {

    private final Long id;

    private final LocalDateTime createdTime;

    private final User author;

    private final Set<User> likes;

    private final Collection<Comment> comments;

    private final PostContent content;

    private final boolean canBeRemoved;

    private final List<String> images;

    private final Integer likesCount;
    private final boolean liked;
    private Integer views;

    public Feed(Post post, String username) {
        this.id = post.getId();
        this.createdTime = post.getCreatedTime();
        this.author = post.getAuthor();
        this.likes = post.getLikes();
        this.comments = post.getComments();
        this.content = post.getPostContent();
        this.canBeRemoved = post.isOwnedBy(username);
        this.images = post.images();
        this.likesCount = likes.size();
        this.liked = post.isLikedBy(username);
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

    public PostContent getContent() {
        return content;
    }

    public boolean canBeRemoved() {
        return canBeRemoved;
    }

    public List<String> images() {
        return images;
    }

    public Integer getLikesCount() {
        return likesCount;
    }

    public boolean isLiked() {
        return liked;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }
}
