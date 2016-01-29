package com.otchi.application.impl;

import com.otchi.application.FeedFetcherService;
import com.otchi.domaine.social.models.Post;
import com.otchi.domaine.social.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
                .sorted((o1, o2) -> o2.getCreationDate().compareTo(o1.getCreationDate()))
                .collect(toList());
    }
}
