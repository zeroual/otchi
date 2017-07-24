package com.otchi.domain.social.repositories;

import com.otchi.domain.social.models.Post;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

import java.time.LocalDateTime;
import java.util.Optional;


@Repository
public interface PostRepository extends CrudRepository<Post, Long> {

     List<Post> findAllByAuthorId(Long id);

    @Query("select p from Post  p inner join  p.postContent Recipe  where p.likes.size = (select max(p.likes.size) from Post  p where p.createdTime>=:yesterday)")
    List<Post> findAllRecipesPostMostLikedAfter(@Param("yesterday") LocalDateTime yesterday);

    //TODO i used this function because i can't limit the results returned by the request above
    default Optional<Post> findFirstRecipePostMostLikedAfter(LocalDateTime yesterday) {
        List<Post> recipes = findAllRecipesPostMostLikedAfter(yesterday);
        if (!recipes.isEmpty())
            return Optional.of(recipes.get(0));
        return Optional.empty();
    }
}
