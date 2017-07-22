package com.otchi.api;


import com.otchi.api.facades.dto.FeedDTO;
import com.otchi.application.Chef;
import com.otchi.application.ChefProfileService;
import com.otchi.application.Feed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.otchi.infrastructure.config.ResourcesPath.CHEF;

@RestController
@RequestMapping(value = CHEF)
public class ChefResource {

    @Autowired
    private ChefProfileService chefProfileService;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Chef getChefInformation(@PathVariable(name = "id") Long id) {
        return chefProfileService.findChef(id);
    }

    @GetMapping("/{id}/feeds")
    @ResponseStatus(HttpStatus.OK)
    public List<FeedDTO> getChefFeeds(@PathVariable(name = "id") Long id){
        List<Feed> feedList = chefProfileService.fetchChefFeeds(id);
        return FeedDTO.constructFeedDTOListFromFeedList(feedList);
    }

}
