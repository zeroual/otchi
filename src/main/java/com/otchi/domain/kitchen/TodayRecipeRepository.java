package com.otchi.domain.kitchen;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TodayRecipeRepository extends CrudRepository<TodayRecipe, Long> {
    Optional<TodayRecipe> findFirstByOrderByDateDesc();
}
