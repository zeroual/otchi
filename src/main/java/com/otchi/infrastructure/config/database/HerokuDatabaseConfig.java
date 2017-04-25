package com.otchi.infrastructure.config.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

import static com.otchi.infrastructure.config.Constants.SPRING_PROFILE_PRODUCTION;

@Configuration
@Profile(SPRING_PROFILE_PRODUCTION)
public class HerokuDatabaseConfig {

    private final Logger log = LoggerFactory.getLogger(HerokuDatabaseConfig.class);

    @Bean
    @ConfigurationProperties(prefix = "spring.dataSource")
    public DataSource dataSource() {
        log.debug("Configuring Heroku Datasource");
        return DataSourceBuilder.create().build();
    }
}
