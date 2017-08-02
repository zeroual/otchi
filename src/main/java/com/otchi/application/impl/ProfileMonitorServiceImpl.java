package com.otchi.application.impl;

import com.otchi.application.ProfileMonitorService;
import com.otchi.domain.analytics.ProfileView;
import com.otchi.domain.analytics.ProfileViewRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ProfileMonitorServiceImpl implements ProfileMonitorService {

    private final ProfileViewRepository profileViewRepository;

    @Autowired
    public ProfileMonitorServiceImpl(ProfileViewRepository profileViewRepository) {
        this.profileViewRepository = profileViewRepository;
    }

    @Override
    public Integer getViewsCountOf(Long profileId) {
        return profileViewRepository.countByViewProfileId(profileId);
    }

    @Transactional
    @Override
    public void incrementViews(ProfileView profileView) {
        profileViewRepository.save(profileView);
    }
}
