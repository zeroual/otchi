package com.otchi.application.impl;

import com.otchi.application.FeedService;
import com.otchi.application.UserService;
import com.otchi.domaine.social.exceptions.PostNotFoundException;
import com.otchi.domaine.social.models.Post;
import com.otchi.domaine.social.repositories.PostRepository;
import com.otchi.domaine.users.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class FeedServiceImpl implements FeedService {

    private PostRepository postRepository;
    private UserService userService;

    @Autowired
    public FeedServiceImpl(PostRepository postRepository, UserService userService) {
        this.postRepository = postRepository;
        this.userService = userService;
    }

    @Override
    public void likePost(Long postId, String username) {
        Post post = postRepository.findOne(postId);
        if(post==null){
            throw new PostNotFoundException(postId);
        }
        User user = userService.findUserByEmail(username).get();
        post.addLike(user);
        postRepository.save(post);
    }

    @Override
    public void unlikePost(long postId, String username) {
        Post post = postRepository.findOne(postId);
        if(post==null){
            throw new PostNotFoundException(postId);
        }
        User user = userService.findUserByEmail(username).get();
        post.unLike(user);
        postRepository.save(post);
    }
}
