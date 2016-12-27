package api.stepsDefinition;

import cucumber.api.java.en.Given;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.time.LocalDateTime.parse;
import static java.time.format.DateTimeFormatter.ofPattern;

public class CommonStepsDefinitions {

    @Autowired
    private MocakableDateFactory mocakableDateFactory;
    private DateTimeFormatter dateTimeFormatter = ofPattern("yyyy-MM-dd HH:mm:ss");


    @Given("^time now is \"([^\"]*)\"$")
    public void timeNowIs(String dateString) throws Throwable {
        LocalDateTime newDate = parse(dateString, dateTimeFormatter);
        mocakableDateFactory.setNowTimeTo(newDate);
    }
}
