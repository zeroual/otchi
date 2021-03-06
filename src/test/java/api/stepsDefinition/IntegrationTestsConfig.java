package api.stepsDefinition;

import com.otchi.application.utils.Clock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.Filter;
import javax.sql.DataSource;

@Configuration
public class IntegrationTestsConfig {


    @Autowired
    protected WebApplicationContext webApplicationContext;

    @Autowired
    private Filter springSecurityFilterChain;

    @Bean
    public MockMvc mockMvc() {
        return MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .addFilter(springSecurityFilterChain)
                .build();

    }

    @Bean
    @Primary
    public Clock clock() {
        return new MocakableClock();
    }

    @Bean
    public DatabaseCleanerForTest databaseCleanerForTest(DataSource datasource) {
        return new DatabaseCleanerForTest(datasource);
    }
}
