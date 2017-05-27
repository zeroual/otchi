package api.stepsDefinition;

import com.otchi.domain.notifications.models.Notification;
import com.otchi.domain.notifications.models.NotificationType;
import com.otchi.domain.notifications.models.NotificationsRepository;
import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.otchi.domain.notifications.models.NotificationType.COMMENT_ON_POST;
import static java.time.format.DateTimeFormatter.ofPattern;
import static org.assertj.core.api.Assertions.assertThat;


public class NotificationsStepDef {

    @Autowired
    private NotificationsRepository notificationsRepository;

    @Given("^those notifications:$")
    public void thoseNotifications(DataTable dataTable) throws Throwable {

        dataTable.asList(NotificationCucumber.class)
                .stream()
                .map(NotificationCucumber::toNotification)
                .forEach(notificationsRepository::save);
    }

    @And("^notification with id \"([^\"]*)\" is marked as read$")
    public void notificationWithIdIsMarkedAsRead(Long id) throws Throwable {
        Notification notification = notificationsRepository.findOne(id);
        assertThat(notification.isUnread()).isFalse();
    }

    @And("^notification with id \"([^\"]*)\" is marked as unread$")
    public void notificationWithIdIsMarkedAsUnread(Long id) throws Throwable {
        Notification notification = notificationsRepository.findOne(id);
        assertThat(notification.isUnread()).isTrue();
    }

    @And("^commented on post notification was sent to post author \"([^\"]*)\"$")
    public void verifyThatCommentedOnPostNotificationWasSentToPostAuthor(String author) throws Throwable {
        Notification notification = notificationsRepository.findOne(1L);
        assertThat(notification.getUsername()).isEqualTo(author);
        assertThat(notification.getType()).isEqualTo(COMMENT_ON_POST);
    }

    private static class NotificationCucumber {

        static DateTimeFormatter dateTimeFormatter = ofPattern("yyyy-MM-dd HH:mm:ss");

        private NotificationType type;
        private String username;
        private boolean unread;
        private String sender;
        private String creationDate;
        private Long postId;

        public Notification toNotification() {

            Notification notification = new Notification(username, sender, postId, type);
            notification.changeCreationDateTo(LocalDateTime.parse(creationDate, dateTimeFormatter));
            if (unread == false)
                notification.markAsRead();
            return notification;
        }
    }
}
