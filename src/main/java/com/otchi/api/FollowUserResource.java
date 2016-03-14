package com.otchi.api;

import com.otchi.api.facades.dto.UserDTO;
import com.otchi.application.FollowUserService;
import com.otchi.infrastructure.config.ResourcesPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping(ResourcesPath.ME)
public class FollowUserResource {

    @Autowired
    private FollowUserService followUserService;

    @RequestMapping(value = "/following/{userId}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void followUser(@PathVariable(value = "userId") Long userId, @AuthenticationPrincipal Principal principal) {
        String followerId = principal.getName();
        followUserService.followUser(followerId, userId);
    }


    @RequestMapping(value = "/following", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<UserDTO> getAllPossibleFollowers(@AuthenticationPrincipal Principal principal) {
        return followUserService.getAllPossibleFollowers(principal.getName())
                .stream()
                .map(UserDTO::new)
                .collect(toList());
    }

}
