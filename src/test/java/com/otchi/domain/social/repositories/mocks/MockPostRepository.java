package com.otchi.domain.social.repositories.mocks;

import com.otchi.application.Feed;
import com.otchi.domain.social.models.Post;
import com.otchi.domain.social.repositories.PostRepository;
import com.otchi.utils.mocks.MockCrudRepository;

import java.util.List;
import java.util.stream.Collectors;

public class MockPostRepository extends MockCrudRepository<Post, Long> implements PostRepository {
    public MockPostRepository() {
        super(Post.class);
    }

    @Override
    public List<Post> findAllByAuthorId(Long id) {
        return this.findAll(post -> post.getAuthor().getId().equals(id))
                .stream()
                .collect(Collectors.toList());
    }
}
