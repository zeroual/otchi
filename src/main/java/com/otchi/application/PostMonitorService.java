package com.otchi.application;

import com.otchi.domain.analytics.PostView;

public interface PostMonitorService {


    Integer getViewsCountOf(Long postId);

    Integer incrementViews(PostView postView);
}
