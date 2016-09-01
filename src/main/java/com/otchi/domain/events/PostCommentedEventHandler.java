package com.otchi.domain.events;

import com.otchi.domain.services.PushNotificationsService;
import com.otchi.domain.social.models.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.bus.Event;
import reactor.fn.Consumer;

@Service
public class PostCommentedEventHandler implements Consumer<Event<PostCommentedEvent>> {

    private final PushNotificationsService pushNotificationsService;

    @Autowired
    public PostCommentedEventHandler(PushNotificationsService pushNotificationsService) {
        this.pushNotificationsService = pushNotificationsService;
    }

    @Override
    public void accept(Event<PostCommentedEvent> postCommentedEvent) {
        PostCommentedEvent event = postCommentedEvent.getData();
        Post post = event.getPost();
        String commentOwner = event.getCommentOwner();
        pushNotificationsService.sendCommentedNotificationToPostAuthor(post, commentOwner);
    }
}
