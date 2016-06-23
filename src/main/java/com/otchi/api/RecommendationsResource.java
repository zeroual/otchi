package com.otchi.api;

import com.otchi.api.facades.dto.UserDTO;
import com.otchi.application.RecommendationService;
import com.otchi.infrastructure.config.ResourcesPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping(ResourcesPath.RECOMMENDATIONS)
public class RecommendationsResource {

    @Autowired
    private RecommendationService recommendationService;

    @RequestMapping(value = "/followings", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<UserDTO> getFollowings(Principal principal) {
        return recommendationService.getRecommendedFollowingsFor(principal.getName())
                .stream()
                .map(UserDTO::new)
                .collect(toList());
    }

}
