package com.otchi.application;

import com.otchi.domaine.social.models.Comment;

public interface FeedService {

    void likePost(Long postId, String username);

    void unlikePost(long postId, String username);

    Comment commentOnPost(Long postId, String content,String username);
}
