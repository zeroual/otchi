package com.otchi.infrastructure.boot;

import com.otchi.infrastructure.config.ApplicationConfig;
import com.otchi.infrastructure.config.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.core.env.SimpleCommandLinePropertySource;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Collection;

@SpringBootApplication
@Configuration
@Import(value = ApplicationConfig.class)
public class OtchiApplicationStarter {

    private static final Logger log = LoggerFactory.getLogger(OtchiApplicationStarter.class);

    @Autowired
    private Environment env;

    /**
     * Main method, used to run the application.
     */
    public static void main(String[] args) throws UnknownHostException {
        SpringApplication app = new SpringApplication(OtchiApplicationStarter.class);
        SimpleCommandLinePropertySource source = new SimpleCommandLinePropertySource(args);
        setDefaultProfile(app, source);
        Environment env = app.run(args).getEnvironment();
        log.info("Access URLs:     \n----------------------------------------------------------\n\t" +
                        "Local:    \t http://127.0.0.1:{}\n\t" +
                        "External: \t http://{}:{}\n----------------------------------------------------------",
                env.getProperty("server.port"),
                InetAddress.getLocalHost().getHostAddress(),
                env.getProperty("server.port"));

    }

    /**
     * If no profile has been configured, set by default the "dev" profile.
     */
    private static void setDefaultProfile(SpringApplication app, SimpleCommandLinePropertySource source) {
        if (!source.containsProperty("spring.profiles.active") &&
                !System.getenv().containsKey("SPRING_PROFILES_ACTIVE")) {
            app.setAdditionalProfiles(Constants.SPRING_PROFILE_DEVELOPMENT);
        }
    }

    /**
     * Initializes the App.
     * Spring profiles can be configured with a program arguments --spring.profiles.active= active-profile
     */

    @PostConstruct
    public void initApplication() throws IOException {
        if (env.getActiveProfiles().length == 0) {
            log.warn("No Spring profile configured, running with default configuration");
        } else {
            log.info("Running with Spring profile(s) : {}", Arrays.toString(env.getActiveProfiles()));
            Collection<String> activeProfiles = Arrays.asList(env.getActiveProfiles());
            if (activeProfiles.contains("dev") && activeProfiles.contains("prod")) {
                log.error("You have misconfigured your application! " +
                        "It should not run with both the 'dev' and 'prod' profiles at the same time.");
            }
        }
    }
}
