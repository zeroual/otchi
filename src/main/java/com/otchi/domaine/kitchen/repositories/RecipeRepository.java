package com.otchi.domaine.kitchen.repositories;

import com.otchi.domaine.kitchen.models.Recipe;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RecipeRepository extends CrudRepository<Recipe,String> {


}
