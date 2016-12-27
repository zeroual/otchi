package api.stepsDefinition;

import com.otchi.application.utils.DateFactory;

import java.time.LocalDateTime;

public class MocakableDateFactory implements DateFactory {
    private LocalDateTime now = LocalDateTime.now();

    @Override
    public LocalDateTime now() {
        return now;
    }

    public void setNowTimeTo(LocalDateTime newDate) {
        this.now = newDate;
    }
}
