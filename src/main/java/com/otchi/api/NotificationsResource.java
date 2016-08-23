package com.otchi.api;

import com.otchi.api.facades.dto.NotificationDTO;
import com.otchi.application.NotificationWithSender;
import com.otchi.application.NotificationsService;
import com.otchi.infrastructure.config.ResourcesPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

import static java.util.stream.Collectors.toList;


@RestController
@RequestMapping(ResourcesPath.ME)
public class NotificationsResource {

    @Autowired
    private NotificationsService notificationsService;

    @RequestMapping(value = "/notifications", method = RequestMethod.GET)
    public List<NotificationDTO> getUserNotifications(Principal principal, @RequestParam(value = "unread", defaultValue = "false") boolean unread) {

        List<NotificationWithSender> allNotificationsOf;
        if (unread) {
            allNotificationsOf = notificationsService.getAllUnreadNotificationsOf(principal.getName());
        } else {
            allNotificationsOf = notificationsService.getAllNotificationsOf(principal.getName());
        }

        return allNotificationsOf
                .stream().map(NotificationDTO::new).collect(toList());
    }
}
