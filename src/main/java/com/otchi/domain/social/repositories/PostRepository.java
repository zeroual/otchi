package com.otchi.domain.social.repositories;

import com.otchi.domain.social.models.Post;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PostRepository extends CrudRepository<Post, Long> {

}
