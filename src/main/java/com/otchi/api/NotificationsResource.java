package com.otchi.api;

import com.otchi.domain.notifications.services.NotificationsService;
import com.otchi.domain.notifications.services.WebNotification;
import com.otchi.infrastructure.config.ResourcesPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;


@RestController
@RequestMapping(ResourcesPath.ME)
public class NotificationsResource {

    @Autowired
    private NotificationsService notificationsService;

    @RequestMapping(value = "/notifications", method = RequestMethod.GET)
    public List<WebNotification> getUserNotifications(Principal principal, @RequestParam(value = "unread", defaultValue = "false") boolean unread) {

        if (unread) {
            return notificationsService.getAllUnreadNotificationsOf(principal.getName());
        } else {
            return notificationsService.getAllNotificationsOf(principal.getName());
        }
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
