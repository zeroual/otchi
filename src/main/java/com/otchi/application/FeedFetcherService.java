package com.otchi.application;

import com.otchi.domaine.social.models.Post;

import java.util.List;


public interface FeedFetcherService {

    List<Post> fetchAllFeeds();
}
