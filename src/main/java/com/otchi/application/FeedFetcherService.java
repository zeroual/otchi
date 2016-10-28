package com.otchi.application;

import java.util.List;
import java.util.Optional;


public interface FeedFetcherService {

    List<Feed> fetchAllFeeds(String username);

    Optional<Feed> getFeed(Long id, String username);
}
