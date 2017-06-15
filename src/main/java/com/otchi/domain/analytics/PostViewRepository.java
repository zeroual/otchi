package com.otchi.domain.analytics;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostViewRepository extends CrudRepository<PostView, ViewId> {

    Integer countByViewPostId(Long postId);
}
