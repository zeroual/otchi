package com.otchi.domain.social.repositories;

import com.otchi.application.Feed;
import com.otchi.domain.social.models.Post;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PostRepository extends CrudRepository<Post, Long> {

    public List<Post> findAllByAuthorId(Long id);

}
