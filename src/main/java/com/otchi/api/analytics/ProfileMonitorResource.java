package com.otchi.api.analytics;

import com.otchi.application.ProfileMonitorService;
import com.otchi.domain.analytics.ProfileView;
import com.otchi.infrastructure.config.ResourcesPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping(value = ResourcesPath.ANALYTICS)
public class ProfileMonitorResource {

    @Autowired
    private ProfileMonitorService profileMonitorService;

    @RequestMapping(value = "/views/profile/{id}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void incrementViews(@PathVariable(value = "id") Long chefId, HttpServletRequest request) {
        String ipAddress = request.getRemoteAddr();
        ProfileView profileView = new ProfileView(chefId, ipAddress);
        profileMonitorService.incrementViews(profileView);
    }
}
