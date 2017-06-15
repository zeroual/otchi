package com.otchi.application.impl;

import com.otchi.application.PostMonitorService;
import com.otchi.domain.analytics.PostView;
import com.otchi.domain.analytics.PostViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class PostMonitorServiceImpl implements PostMonitorService {

    private final PostViewRepository postViewRepository;

    @Autowired
    public PostMonitorServiceImpl(PostViewRepository postViewRepository) {
        this.postViewRepository = postViewRepository;
    }

    @Override
    public Integer getViewsCountOf(Long postId) {
        return postViewRepository.countByViewPostId(postId);
    }

    @Transactional
    @Override
    public Integer incrementViews(PostView postView) {
        postViewRepository.save(postView);
        return postViewRepository.countByViewPostId(postView.getPostId());
    }
}
