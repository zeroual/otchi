package com.otchi.domain.social.events;

import com.otchi.domain.notifications.events.Event;

public class PostDeletedEvent implements Event {
    private Long postId;

    public PostDeletedEvent(Long postId) {
        this.postId = postId;
    }

    public Long getPostId() {
        return postId;
    }
}
