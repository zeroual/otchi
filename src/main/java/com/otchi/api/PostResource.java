package com.otchi.api;

import com.otchi.api.facades.dto.PostDTO;
import com.otchi.api.facades.dto.RecipeDTO;
import com.otchi.application.PublicationsService;
import com.otchi.application.utils.DateFactory;
import com.otchi.domaine.social.models.Post;
import com.otchi.infrastructure.config.ResourcesPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = ResourcesPath.POST)
public class PostResource {

    @Autowired
    private PublicationsService publicationsService;

    @Autowired
    private DateFactory dateFactory;

    @RequestMapping(value = "/recipe", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public PostDTO publishNewRecipeAsPost(@RequestBody RecipeDTO recipeDTO) {
        Post newPost = new Post(dateFactory.now());
        newPost.withRecipe(recipeDTO.toDomain());
        Post savedPost = publicationsService.createNewPost(newPost);
        return new PostDTO(savedPost);
    }


}
