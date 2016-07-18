package api.stepsDefinition;

import cucumber.api.java.en.Given;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class CommonStepsDefinitions {

    @Autowired
    private MocakableDateFactory mocakableDateFactory;


    @Given("^time now is \"([^\"]*)\"$")
    public void timeNowIs(String dateString) throws Throwable {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date newDate = simpleDateFormat.parse(dateString);
        mocakableDateFactory.setNowTimeTo(newDate);
    }
}
