package com.otchi.application.impl;

import com.otchi.application.FeedService;
import com.otchi.domaine.social.models.Post;
import com.otchi.domaine.social.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeedServiceImpl implements FeedService {

    private PostRepository postRepository;

    @Autowired
    public FeedServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public void likePost(Long postId, Long userId) {
        Post p = postRepository.findOne(postId);
        p.addLike(userId);
        postRepository.save(p);
    }
}
