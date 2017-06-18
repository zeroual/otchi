package com.otchi.api.analytics;

import com.otchi.application.PostMonitorService;
import com.otchi.domain.analytics.PostView;
import com.otchi.infrastructure.config.ResourcesPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping(value = ResourcesPath.ANALYTICS)
public class PostMonitorResource {

    @Autowired
    private PostMonitorService postMonitorService;

    @RequestMapping(value = "/views/feed/{id}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public Integer incrementViews(@PathVariable(value = "id") Long postId, HttpServletRequest request) {
        String ipAddress = request.getRemoteAddr();
        PostView postView = new PostView(postId, ipAddress);
        return postMonitorService.incrementViews(postView);
    }
}
