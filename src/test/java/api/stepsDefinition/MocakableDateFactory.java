package api.stepsDefinition;

import com.otchi.application.utils.DateFactory;

import java.util.Date;

public class MocakableDateFactory implements DateFactory {
    private Date now = new Date();

    @Override
    public Date now() {
        return now;
    }

    public void setNowTimeTo(Date newDate) {
        this.now = newDate;
    }
}
