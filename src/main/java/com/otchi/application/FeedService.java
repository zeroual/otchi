package com.otchi.application;


public interface FeedService {
    void likePost(Long postId, String username);

    void unlikePost(long postId, String username);
}
