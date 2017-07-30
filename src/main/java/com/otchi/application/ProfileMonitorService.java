package com.otchi.application;

import com.otchi.domain.analytics.ProfileView;

public interface ProfileMonitorService {


    Integer getViewsCountOf(Long postId);

    void incrementViews(ProfileView postView);
}
