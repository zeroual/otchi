package com.otchi.application;

import com.otchi.domain.social.models.Post;

import java.util.List;
import java.util.Optional;


public interface FeedFetcherService {

    List<Post> fetchAllFeeds();

    Optional<Post> getFeed(Long id);
}
