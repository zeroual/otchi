package com.otchi.domaine.social.repositories;

import com.otchi.domaine.social.models.Post;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post, Long> {

}
