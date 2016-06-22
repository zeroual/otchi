package com.otchi.api;

import com.otchi.application.FollowingService;
import com.otchi.infrastructure.config.ResourcesPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping(ResourcesPath.ME)
public class FollowingResource {

    @Autowired
    private FollowingService followingService;

    @RequestMapping(value = "/following", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void followUser(@RequestBody Long followingId, Principal principal) {
        String followerId = principal.getName();
        followingService.followUser(followerId, followingId);
    }

}
