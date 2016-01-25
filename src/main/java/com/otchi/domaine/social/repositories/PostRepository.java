package com.otchi.domaine.social.repositories;

import com.otchi.domaine.social.models.Post;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PostRepository extends CrudRepository<Post, Long> {

}
