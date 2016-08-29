package com.otchi.application.impl;

import com.otchi.application.FeedService;
import com.otchi.application.PushNotificationsService;
import com.otchi.application.UserService;
import com.otchi.application.utils.DateFactory;
import com.otchi.domain.social.exceptions.PostNotFoundException;
import com.otchi.domain.social.models.Comment;
import com.otchi.domain.social.models.Post;
import com.otchi.domain.social.repositories.PostRepository;
import com.otchi.domain.users.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class FeedServiceImpl implements FeedService {

    private PostRepository postRepository;
    private UserService userService;
    private PushNotificationsService pushNotificationsService;
    private DateFactory dateFactory;

    @Autowired
    public FeedServiceImpl(PostRepository postRepository, UserService userService,
                           PushNotificationsService pushNotificationsService, DateFactory dateFactory) {
        this.postRepository = postRepository;
        this.userService = userService;
        this.pushNotificationsService = pushNotificationsService;
        this.dateFactory = dateFactory;
    }

    @Override
    public void likePost(Long postId, String likerUsername) {
        Post post = postRepository.findOne(postId);
        if (post == null) {
            throw new PostNotFoundException(postId);
        }
        User user = userService.findUserByUsername(likerUsername).get();
        if (post.isNotAlreadyLikedBy(likerUsername)) {
            post.addLike(user);
            postRepository.save(post);
            pushNotificationsService.sendLikeNotificationToPostAuthor(post, likerUsername);
        }
    }

    @Override
    public void unlikePost(long postId, String username) {
        Post post = postRepository.findOne(postId);
        if (post == null) {
            throw new PostNotFoundException(postId);
        }
        User user = userService.findUserByUsername(username).get();
        post.unLike(user);
        postRepository.save(post);
    }

    @Override
    public Comment commentOnPost(Long postId, String content, String username) {
        Post commentedPost = postRepository.findOne(postId);
        if (commentedPost == null) {
            throw new PostNotFoundException(postId);
        }
        User author = userService.findUserByUsername(username).get();
        Comment comment = new Comment(author, content, dateFactory.now());
        commentedPost.addComment(comment);
        postRepository.save(commentedPost);
        return comment;
    }


}
