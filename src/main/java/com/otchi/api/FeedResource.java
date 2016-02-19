package com.otchi.api;

import com.otchi.api.facades.dto.PostDTO;
import com.otchi.application.FeedFetcherService;
import com.otchi.application.FeedService;
import com.otchi.domaine.users.models.User;
import com.otchi.domaine.users.models.UserRepository;
import com.otchi.infrastructure.config.ResourcesPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping(value = ResourcesPath.FEED)
public class FeedResource {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FeedService feedService;

    @Autowired
    private FeedFetcherService feedFetcherService;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<PostDTO> fetchAllRecipe() {
        return feedFetcherService.fetchAllFeeds()
                .stream()
                .map(PostDTO::new)
                .collect(toList());
    }

    @RequestMapping(value = "/{postId}/like", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void likePost(@PathVariable(value = "postId") String postId, @AuthenticationPrincipal Principal principal){

        String currentUserName = principal.getName();
        Optional<User> currentUser = userRepository.findOneByEmail(currentUserName);
        User user = currentUser.get();
        feedService.likePost(postId, user.getId());
    }
}
