package com.otchi.domain.events;

import com.google.common.eventbus.Subscribe;
import com.otchi.application.MailService;
import com.otchi.application.UserService;
import com.otchi.domain.services.PushNotificationsService;
import com.otchi.domain.social.models.Post;
import com.otchi.domain.users.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PushNotificationEventHandler {

    private final PushNotificationsService pushNotificationsService;
    private final ConnectedUserService connectedUserService;
    private final MailService mailService;
    private final UserService userService;

    @Autowired
    public PushNotificationEventHandler(PushNotificationsService pushNotificationsService,
                                        ConnectedUserService connectedUserService,
                                        MailService mailService,
                                        UserService userService) {
        this.pushNotificationsService = pushNotificationsService;
        this.connectedUserService = connectedUserService;
        this.mailService = mailService;
        this.userService = userService;
    }


    @Subscribe
    public void sendCommentedNotificationToPostAuthor(PostCommentedEvent postCommentedEvent) {
        PostCommentedEvent event = postCommentedEvent;
        Post post = event.getPost();
        String commentOwner = event.getCommentOwner();
        if (!post.isOwnedBy(commentOwner)) {
            pushNotificationsService.sendCommentedNotificationToPostAuthor(post, commentOwner);
        }
    }

    @Subscribe
    public void sendLikeNotificationToPostAuthor(LikePostEvent likePostEvent) {
        LikePostEvent event = likePostEvent;
        Post likedPost = event.getLikedPost();
        String likeOwner = event.getLikeOwner();
        if (!likedPost.isOwnedBy(likeOwner)) {
            String authorUsername = likedPost.getAuthor().getUsername();
            if (connectedUserService.isConnected(authorUsername)) {
                pushNotificationsService.sendLikeNotificationToPostAuthor(likedPost, likeOwner);
            }
            else {


                User liker = userService.findUserByUsername(likeOwner).orElseThrow(()-> new RuntimeException("to change !!"));
                User author = likedPost.getAuthor();
                mailService.sendLikedPostNotificationMail(author,liker,null,null);
            }
        }
    }

}
