package com.otchi.application.impl;

import com.otchi.application.PublicationsService;
import com.otchi.domaine.social.models.Post;
import com.otchi.domaine.social.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PublicationsServiceImpl implements PublicationsService {

    private PostRepository postRepository;

    @Autowired
    public PublicationsServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public Post createNewPost(Post post) {
        return postRepository.save(post);
    }
}
