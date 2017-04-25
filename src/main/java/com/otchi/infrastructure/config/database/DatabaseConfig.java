package com.otchi.infrastructure.config.database;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories("com.otchi.domain")
@EntityScan("com.otchi.domain")
@Import({HerokuDatabaseConfig.class, H2DatabaseConfig.class})
public class DatabaseConfig {


}
