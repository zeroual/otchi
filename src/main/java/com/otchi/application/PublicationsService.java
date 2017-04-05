package com.otchi.application;

import com.otchi.domain.kitchen.Recipe;
import com.otchi.domain.social.models.Story;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PublicationsService {
    Feed publishRecipe(Recipe recipe, List<MultipartFile> pictures, String username);

    Feed publishStory(Story story, List<MultipartFile> images, String author);
}
