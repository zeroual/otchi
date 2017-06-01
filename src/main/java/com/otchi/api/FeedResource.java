package com.otchi.api;

import com.otchi.api.facades.dto.CommentDTO;
import com.otchi.api.facades.dto.FeedDTO;
import com.otchi.api.facades.exceptions.ResourceNotFoundException;
import com.otchi.application.Feed;
import com.otchi.application.FeedFetcherService;
import com.otchi.application.FeedService;
import com.otchi.domain.social.models.Comment;
import com.otchi.infrastructure.config.ResourcesPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping(value = ResourcesPath.FEED)
public class FeedResource {

    @Autowired
    private FeedService feedService;

    @Autowired
    private FeedFetcherService feedFetcherService;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<FeedDTO> fetchAllRecipe(Principal principal) {

        //TODO : refactor: the empty string don't really mean that user is unauthenticated
        String username = "";

        if (principal != null) {
            username = principal.getName();
        }

        return feedFetcherService.fetchAllFeeds(username)
                .stream()
                .map(FeedDTO::new)
                .collect(toList());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public FeedDTO fetchFeed(@PathVariable("id") Long feedId, Principal principal) {
        String username = principal.getName();
        Optional<Feed> feedOptional = feedFetcherService.getFeed(feedId, username);
        Feed feed = feedOptional.orElseThrow(() -> new ResourceNotFoundException(feedId));
        return new FeedDTO(feed);
    }

    @RequestMapping(value = "/{postId}/like", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void likePost(@PathVariable(value = "postId") Long postId, Principal principal) {
        String username = principal.getName();
        feedService.likePost(postId, username);
    }

    @RequestMapping(value = "/{postId}/unlike", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void unLikePost(@PathVariable(value = "postId") Long postId, Principal principal) {
        String username = principal.getName();
        feedService.unlikePost(postId, username);
    }

    @RequestMapping(value = "/{postId}/comment", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDTO commentOnPost(@PathVariable(value = "postId") Long postId,
                                    @RequestBody String commentContent,
                                    Principal principal) {
        String username = principal.getName();
        Comment savedComment = feedService.commentOnPost(postId, commentContent, username);
        return new CommentDTO(savedComment);
    }

    @RequestMapping(value = "/{postId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deletePost(@PathVariable(value = "postId") Long postId, Principal principal) {
        String username = principal.getName();
        feedService.deletePost(postId, username);
    }
}
