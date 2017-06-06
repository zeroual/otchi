package com.otchi.api;


import com.otchi.application.Chef;
import com.otchi.application.ChefProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
}
