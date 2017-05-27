package com.otchi.domain.notifications.events;

import com.otchi.domain.social.models.Post;

public class LikePostEvent implements Event {
    private final Post likedPost;
    private final String likeOwner;

    public LikePostEvent(Post likedPost, String likeOwner) {
        this.likedPost = likedPost;
        this.likeOwner = likeOwner;
    }

    public Post getLikedPost() {
        return likedPost;
    }

    public String getLikeOwner() {
        return likeOwner;
    }
}
