package api;

import com.otchi.infrastructure.config.ApplicationConfig;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"pretty", "html:target/cucumber"},
        features = {"classpath:features/api/"},
        glue = {"api/stepsDefinition"}
)
@ContextConfiguration(classes = {ApplicationConfig.class})

public class CucumberIntegrationTest {

}
