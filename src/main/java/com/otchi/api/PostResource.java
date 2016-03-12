package com.otchi.api;

import com.otchi.api.facades.dto.PostDTO;
import com.otchi.api.facades.dto.RecipeDTO;
import com.otchi.application.PublicationsService;
import com.otchi.domain.kitchen.models.Recipe;
import com.otchi.domain.social.models.Post;
import com.otchi.infrastructure.config.ResourcesPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@RestController
@RequestMapping(value = ResourcesPath.POST)
public class PostResource {

    @Autowired
    private PublicationsService publicationsService;

    @RequestMapping(value = "/recipe", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public PostDTO publishNewRecipeAsPost(@RequestBody RecipeDTO recipeDTO, @AuthenticationPrincipal Principal principal) {
        Recipe recipe = recipeDTO.toDomain();
        Post savedPost = publicationsService.publishRecipe(recipe, principal.getName());
        return new PostDTO(savedPost);
    }


}
