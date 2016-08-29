package com.otchi.api;

import com.otchi.api.facades.dto.NotificationDTO;
import com.otchi.application.NotificationWithSender;
import com.otchi.application.NotificationsService;
import com.otchi.infrastructure.config.ResourcesPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(value = "/notifications/{id}", method = RequestMethod.PUT)
    public void changeNotificationUnreadStatus(@PathVariable("id") Long id,
                                               @RequestBody NotificationUnreadState newNotificationUnreadState,
                                               Principal principal) {
        String username = principal.getName();
        if (newNotificationUnreadState.isUnread()) {
            notificationsService.markNotificationAsUnread(id, username);
        } else {
            notificationsService.markNotificationAsRead(id, username);
        }
    }

    public static class NotificationUnreadState {

        private boolean unread;

        public NotificationUnreadState() {
        }

        public boolean isUnread() {
            return unread;
        }

        public void setUnread(boolean unread) {
            this.unread = unread;
        }
    }
}
