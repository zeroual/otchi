package com.zeros.domain.kitchen.repositories;

import com.zeros.domain.kitchen.models.Recipe;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RecipeRepository extends CrudRepository<Recipe,Long>{


}
