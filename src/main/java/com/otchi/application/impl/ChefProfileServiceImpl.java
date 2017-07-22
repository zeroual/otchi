package com.otchi.application.impl;

import com.otchi.application.*;
import com.otchi.domain.users.exceptions.UserNotFoundException;
import com.otchi.domain.users.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChefProfileServiceImpl implements ChefProfileService {

    private final UserService userService;

    private final FeedFetcherService feedFetcherService;

    @Autowired
    public ChefProfileServiceImpl(UserService userService, FeedFetcherService feedFetcherService) {
        this.userService = userService;
        this.feedFetcherService = feedFetcherService;
    }

    @Override
    public Chef findChef(Long id) {
        Optional<User> userById = userService.findUserById(id);
        User user = userById.orElseThrow(() -> new UserNotFoundException(id.toString()));
        return new Chef(user.getId(), user.getFirstName(), user.getLastName(), user.picture());
    }

    @Override
    public List<Feed> fetchChefFeeds(Long id) {
        return feedFetcherService.fetchAllFeedsForUser(id);
    }
}
