package com.otchi.application;

import com.otchi.domain.kitchen.models.Recipe;
import com.otchi.domain.social.models.Post;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PublicationsService {
    Post publishRecipe(Recipe recipe, List<MultipartFile> pictures, String username);
}
