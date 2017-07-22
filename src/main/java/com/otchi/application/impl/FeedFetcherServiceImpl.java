package com.otchi.application.impl;

import com.otchi.application.Feed;
import com.otchi.application.FeedFetcherService;
import com.otchi.application.PostMonitorService;
import com.otchi.domain.social.models.Post;
import com.otchi.domain.social.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

@Service
public class FeedFetcherServiceImpl implements FeedFetcherService {

    private final PostMonitorService postMonitorService;

    private final PostRepository postRepository;

    @Autowired
    public FeedFetcherServiceImpl(PostMonitorService postMonitorService, PostRepository postRepository) {
        this.postMonitorService = postMonitorService;
        this.postRepository = postRepository;
    }

    @Override
    public List<Feed> fetchAllFeeds(String username) {
        return StreamSupport
                .stream(postRepository.findAll().spliterator(), true)
                .map(post -> new Feed(post, username))
                .sorted((o1, o2) -> o2.getCreatedTime().compareTo(o1.getCreatedTime()))
                .collect(toList());
    }

    @Override
    public Optional<Feed> getFeed(Long id, String username) {
        Post post = postRepository.findOne(id);
        if (post == null) {
            return Optional.empty();
        } else {
            Feed feed = new Feed(post, username);
            Integer viewsCount = postMonitorService.getViewsCountOf(id);
            feed.setViews(viewsCount);
            return ofNullable(feed);
        }
    }

    @Override
    public List<Feed> fetchAllFeedsForUser(Long userId) {

        return StreamSupport
                .stream(postRepository.findAllByAuthorId(userId).spliterator(), true)
                .map(post -> new Feed(post, post.getAuthor().getUsername()))
                .sorted((p1, p2) -> p2.getCreatedTime().compareTo(p1.getCreatedTime()))
                .collect(toList());
    }
}
