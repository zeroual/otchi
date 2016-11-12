package com.otchi.application.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;
import com.otchi.application.FeedService;
import com.otchi.application.UserService;
import com.otchi.application.utils.DateFactory;
import com.otchi.domain.events.DomainEvents;
import com.otchi.domain.events.LikePostEvent;
import com.otchi.domain.events.PostCommentedEvent;
import com.otchi.domain.social.exceptions.PostNotFoundException;
import com.otchi.domain.social.exceptions.ResourceNotAuthorizedException;
import com.otchi.domain.social.models.Comment;
import com.otchi.domain.social.models.Post;
import com.otchi.domain.social.repositories.PostRepository;
import com.otchi.domain.users.models.User;

@Service
public class FeedServiceImpl implements FeedService {

    private final PostRepository postRepository;
    private final UserService userService;
    private final DateFactory dateFactory;
    private final DomainEvents domainEvents;

    @Autowired
    public FeedServiceImpl(PostRepository postRepository,
                           UserService userService,
                           DateFactory dateFactory,
                           DomainEvents domainEvents) {
        this.postRepository = postRepository;
        this.userService = userService;
        this.dateFactory = dateFactory;
        this.domainEvents = domainEvents;
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
            LikePostEvent likePostEvent = new LikePostEvent(post, likerUsername);
            domainEvents.raise(likePostEvent);
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
    public void deletePost(Long postId, String username) {
        Post post = postRepository.findOne(postId);
        if (post == null) {
            throw new PostNotFoundException(postId);
        }
        else if (!post.isOwnedBy(username)){
            throw new ResourceNotAuthorizedException("Sorry ! this content is private.");
        }
        postRepository.delete(post);
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
		Post savedPost = postRepository.save(commentedPost);
		PostCommentedEvent postCommentedEvent = new PostCommentedEvent(
				savedPost, username);
		domainEvents.raise(postCommentedEvent);
		return comment;
	}

}
