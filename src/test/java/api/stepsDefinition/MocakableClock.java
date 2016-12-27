package api.stepsDefinition;

import com.otchi.application.utils.Clock;

import java.time.LocalDateTime;

public class MocakableClock implements Clock {
    private LocalDateTime now = LocalDateTime.now();

    @Override
    public LocalDateTime now() {
        return now;
    }

    public void setNowTimeTo(LocalDateTime newDate) {
        this.now = newDate;
    }
}
