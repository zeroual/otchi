package com.otchi.application;


import org.springframework.stereotype.Service;

@Service
public interface FeedService {
    void likePost(Long postId, Long userId);
}
