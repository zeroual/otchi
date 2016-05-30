package com.otchi.infrastructure.config.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContextException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

import static com.otchi.infrastructure.config.Constants.SPRING_PROFILE_PRODUCTION;

@Configuration
@Profile(SPRING_PROFILE_PRODUCTION)
public class HerokuDatabaseConfig {

    private final Logger log = LoggerFactory.getLogger(HerokuDatabaseConfig.class);

    @Value("${spring.datasource.cachePrepStmts:true}")
    private boolean cachePrepStmts;

    @Value("${spring.datasource.prepStmtCacheSize:250}")
    private int prepStmtCacheSize;

    @Value("${spring.datasource.prepStmtCacheSqlLimit:2048}")
    private int prepStmtCacheSqlLimit;

    @Value("${spring.datasource.dataSourceClassName}")
    private String dataSourceClassName;

    @Bean
    public DataSource dataSource() {
        log.debug("Configuring Heroku Datasource");
        String herokuUrl = System.getenv("JDBC_DATABASE_URL");
        if (herokuUrl != null) {
            HikariConfig config = new HikariConfig();

            //MySQL optimizations, see https://github.com/brettwooldridge/HikariCP/wiki/MySQL-Configuration
            if ("com.mysql.jdbc.jdbc2.optional.MysqlDataSource".equals(dataSourceClassName)) {
                config.addDataSourceProperty("cachePrepStmts", cachePrepStmts);
                config.addDataSourceProperty("prepStmtCacheSize", prepStmtCacheSize);
                config.addDataSourceProperty("prepStmtCacheSqlLimit", prepStmtCacheSqlLimit);
            }

            config.setDataSourceClassName(dataSourceClassName);
            config.addDataSourceProperty("url", herokuUrl);
            HikariDataSource hikariDataSource = new HikariDataSource(config);
            executeScript(hikariDataSource, "database/schema.sql");
            executeScript(hikariDataSource, "database/social-users-connections.sql");
            executeScript(hikariDataSource, "database/data.sql");
            return hikariDataSource;
        } else {
            throw new ApplicationContextException("Heroku database URL is not configured, you must set $JDBC_DATABASE_URL");
        }
    }

    public void executeScript(DataSource dataSource, String script) {
        Resource resource = new ClassPathResource(script);
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator(resource);
        databasePopulator.execute(dataSource);
    }
}
