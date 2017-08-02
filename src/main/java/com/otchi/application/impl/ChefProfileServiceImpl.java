package com.otchi.application.impl;

import com.otchi.application.ChefProfileService;
import com.otchi.application.Feed;
import com.otchi.application.FeedFetcherService;
import com.otchi.application.UserService;
import com.otchi.domain.analytics.ProfileViewRepository;
import com.otchi.domain.kitchen.Chef;
import com.otchi.domain.users.exceptions.UserNotFoundException;
import com.otchi.domain.users.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChefProfileServiceImpl implements ChefProfileService {

    private final UserService userService;
    private ProfileViewRepository profileViewRepo;
    private final FeedFetcherService feedFetcherService;

    @Autowired
    public ChefProfileServiceImpl(UserService userService,
                                  FeedFetcherService feedFetcherService,
                                  ProfileViewRepository profileViewRepository) {
        this.userService = userService;
        this.feedFetcherService = feedFetcherService;
        this.profileViewRepo = profileViewRepository;
    }

    @Override
    public Chef findChef(Long id) {
        Optional<User> userById = userService.findUserById(id);

        Integer profileViews = profileViewRepo.countByViewProfileId(id);

        User user = userById.orElseThrow(() -> new UserNotFoundException(id.toString()));
        Chef chef = new Chef(user);
        chef.setViews(profileViews);
        return chef;
    }

    @Override
    public List<Feed> fetchChefFeeds(Long id) {
        return feedFetcherService.fetchAllFeedsForUser(id);
    }
}
