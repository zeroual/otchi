package api.stepsDefinition;

import cucumber.api.java.en.Given;
import org.springframework.beans.factory.annotation.Autowired;

public class CommonStepsDefinitions {

    @Autowired
    private MocakableClock mocakableClock;


    @Given("^time now is \"([^\"]*)\"$")
    public void timeNowIs(String dateString) throws Throwable {
        mocakableClock.setNowTimeTo(dateString);
    }
}
