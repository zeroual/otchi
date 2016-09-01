package com.otchi.domain.events;

import com.otchi.domain.social.models.Post;

public class PostCommentedEvent implements Event {

    private final Post post;
    private final String commentOwner;

    public PostCommentedEvent(Post post, String commentOwner) {
        this.post = post;
        this.commentOwner = commentOwner;
    }

    public Post getPost() {
        return post;
    }

    public String getCommentOwner() {
        return commentOwner;
    }
}
