package com.otchi.application;

import com.otchi.domaine.kitchen.models.Recipe;
import com.otchi.domaine.social.models.Post;

public interface PublicationsService {
    Post publishRecipe(Recipe recipe, String username);
}
