package api.stepsDefinition;

import com.otchi.infrastructure.boot.OtchiApplicationStarter;
import cucumber.api.java.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.ConfigFileApplicationContextInitializer;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;

import static com.otchi.infrastructure.config.Constants.SPRING_PROFILE_DEVELOPMENT;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {OtchiApplicationStarter.class, IntegrationTestsConfig.class},
        initializers = ConfigFileApplicationContextInitializer.class)
@WebAppConfiguration()
@IntegrationTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class})
@ActiveProfiles(SPRING_PROFILE_DEVELOPMENT)

public class SpringIntegrationTestsRunner {

    @Autowired
    private DatabaseCleanerForTest databaseCleaner;

    @Before
    public void setUp() throws Exception {
        databaseCleaner.resetDatabase();
    }
}
