package com.otchi.application;

import java.util.List;
import java.util.Optional;


public interface FeedFetcherService {

    //TODO move this method to another class
    List<Feed> fetchAllFeeds(String username);

    List<Feed> fetchAllFeedsForUser(Long userId);

    Optional<Feed> getFeed(Long id, String username);

}
