package api.stepsDefinition;

import com.otchi.application.utils.Clock;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.time.LocalDateTime.parse;
import static java.time.format.DateTimeFormatter.ofPattern;

public class MocakableClock implements Clock {
    private LocalDateTime now = LocalDateTime.now();

    public MocakableClock() {
    }

    public MocakableClock(LocalDateTime now) {
        this.now = now;
    }

    @Override
    public LocalDateTime now() {
        return now;
    }

    public LocalDateTime setNowTimeTo(String dateString) {
        DateTimeFormatter dateTimeFormatter = ofPattern("yyyy-MM-dd HH:mm:ss");
        now = parse(dateString, dateTimeFormatter);
        return now;
    }
}
