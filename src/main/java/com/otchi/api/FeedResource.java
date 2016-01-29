package com.otchi.api;

import com.otchi.api.facades.dto.PostDTO;
import com.otchi.application.FeedFetcherService;
import com.otchi.infrastructure.config.ResourcesPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping(value = ResourcesPath.FEED)
public class FeedResource {

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

}
