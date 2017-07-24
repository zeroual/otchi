package com.otchi.domain.social.repositories.mocks;

import com.otchi.domain.social.models.Post;
import com.otchi.domain.social.repositories.PostRepository;
import com.otchi.utils.mocks.MockCrudRepository;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

    @Override
    public List<Post> findAllRecipesPostMostLikedAfter(LocalDateTime yesterday) {
        throw new NotImplementedException();
    }

    @Override
    public Optional<Post> findFirstRecipePostMostLikedAfter(LocalDateTime yesterday) {
        throw new NotImplementedException();
    }
}
