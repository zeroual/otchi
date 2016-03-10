package com.otchi.domain.social.repositories.mocks;

import com.otchi.domain.social.models.Post;
import com.otchi.domain.social.repositories.PostRepository;
import com.otchi.utils.mocks.MockCrudRepository;

public class MockPostRepository extends MockCrudRepository<Post, Long> implements PostRepository {
    public MockPostRepository() {
        super(Post.class);
    }
}
