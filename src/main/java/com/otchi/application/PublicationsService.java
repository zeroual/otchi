package com.otchi.application;

import com.otchi.domain.kitchen.models.Recipe;
import com.otchi.domain.social.models.Post;

public interface PublicationsService {
    Post publishRecipe(Recipe recipe, String username);
}
