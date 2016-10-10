package api.stepsDefinition;

import com.otchi.utils.DateParser;
import cucumber.api.java.en.Given;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class CommonStepsDefinitions {

    @Autowired
    private MocakableDateFactory mocakableDateFactory;

    @Autowired
    private World world;


    @Given("^time now is \"([^\"]*)\"$")
    public void timeNowIs(String dateString) throws Throwable {
        Date newDate = DateParser.parse(dateString);
        mocakableDateFactory.setNowTimeTo(newDate);
        world.setCurrentDate(newDate);
    }
}
