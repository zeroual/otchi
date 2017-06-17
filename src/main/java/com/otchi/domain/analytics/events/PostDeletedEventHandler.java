package com.otchi.domain.analytics.events;

import com.google.common.eventbus.Subscribe;
import com.otchi.domain.analytics.PostViewRepository;
import com.otchi.domain.social.events.PostDeletedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class PostDeletedEventHandler {


    private final PostViewRepository postViewRepository;

    @Autowired
    public PostDeletedEventHandler(PostViewRepository postViewRepository) {
        this.postViewRepository = postViewRepository;
    }

    @Subscribe
    @Transactional
    public void removePostViewsCount(PostDeletedEvent postDeletedEvent) {
        Long postId = postDeletedEvent.getPostId();
        postViewRepository.deleteByViewPostId(postId);
    }
}
