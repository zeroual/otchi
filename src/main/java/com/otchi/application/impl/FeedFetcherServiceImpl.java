package com.otchi.application.impl;

import com.otchi.application.FeedFetcherService;
import com.otchi.domain.social.models.Post;
import com.otchi.domain.social.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;

@Service
public class FeedFetcherServiceImpl implements FeedFetcherService {

    private PostRepository postRepository;

    @Autowired
    public FeedFetcherServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public List<Post> fetchAllFeeds() {
        return StreamSupport
                .stream(postRepository.findAll().spliterator(), true)
                .sorted((o1, o2) -> o2.getCreatedTime().compareTo(o1.getCreatedTime()))
                .collect(toList());
    }

    @Override
    public Optional<Post> getFeed(Long id) {
        Post post = postRepository.findOne(id);
        return Optional.ofNullable(post);
    }
}
