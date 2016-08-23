package api.stepsDefinition;

import com.otchi.domain.social.models.Notification;
import com.otchi.domain.social.models.NotificationType;
import com.otchi.domain.social.repositories.NotificationsRepository;
import com.otchi.utils.DateParser;
import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.util.Date;

import static org.junit.Assert.fail;


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

    private class NotificationCucumber {

        private NotificationType type;
        private String username;
        private boolean unread;
        private String sender;
        private String creationDate;
        private Long postId;

        public Notification toNotification() {
            Notification notification = new Notification(username, sender, postId, type);
            notification.changeCreationDateTo(getCreationDate());
            if (unread == false)
                notification.setToRead();

            return notification;
        }

        public Date getCreationDate() {
            try {
                return DateParser.parse(creationDate);
            } catch (ParseException e) {
                fail("bad notification creation date format :( " + creationDate);
            }
            return null;
        }
    }
}
