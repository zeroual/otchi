package com.otchi.infrastructure.config.database;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

import static com.otchi.infrastructure.config.Constants.SPRING_PROFILE_DEVELOPMENT;

@Configuration
@Profile(SPRING_PROFILE_DEVELOPMENT)
public class H2DatabaseConfig {


    @Bean
    @Profile(SPRING_PROFILE_DEVELOPMENT)
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .build();
    }


//    @Bean
//    @Profile(SPRING_PROFILE_DEVELOPMENT)
//    public Server serverH2() throws SQLException {
//        final String H2_SERVE_PORT = "8082";
//        // start a Web server : either before or after opening the database
//        System.out.println("\n>>>>> serverH2 : démarre ..............\n" +
//                "   URL      : http://localhost:" + H2_SERVE_PORT + "/ \n" +
//                "   URL JDBC : jdbc:h2:mem:testdb  \n" +
//                "   USER     : sa \n" +
//                "   PASSWORD : \n" +
//                " \n");
//        return Server.createWebServer("-webAllowOthers", "-webPort", H2_SERVE_PORT).start();
//    }
}
