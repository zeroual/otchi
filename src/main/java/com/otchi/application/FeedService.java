package com.otchi.application;

import com.otchi.domain.social.models.Comment;

public interface FeedService {

    void likePost(Long postId, String username);

    void unlikePost(long postId, String username);

    Comment commentOnPost(Long postId, String content,String username);

    void deletePost(Long postId, String username);
}
