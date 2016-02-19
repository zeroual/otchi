package com.otchi.domaine.social.repositories.mocks;

import com.otchi.domaine.social.models.Post;
import com.otchi.domaine.social.repositories.PostRepository;
import com.otchi.utils.mocks.MockCrudRepository;

public class MockPostRepository extends MockCrudRepository<Post, String> implements PostRepository {
    public MockPostRepository() {
        super(Post.class);
    }
}
