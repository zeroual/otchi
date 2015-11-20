package com.otchi.domain.kitchen.repositories;

import com.otchi.domain.kitchen.models.Recipe;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RecipeRepository extends CrudRepository<Recipe,Long>{


}
