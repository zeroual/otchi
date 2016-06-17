package com.otchi.domain.kitchen;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RecipeRepository extends CrudRepository<Recipe, Long> {


}
