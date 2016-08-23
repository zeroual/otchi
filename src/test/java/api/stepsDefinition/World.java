package api.stepsDefinition;

import java.util.Date;

public class World {


    private Date now = new Date();

    void setCurrentDate(Date now) {
        this.now = now;
    }

    Date currentDate() {
        return now;
    }
}
