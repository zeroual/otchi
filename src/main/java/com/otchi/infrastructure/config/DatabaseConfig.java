package com.otchi.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;


@Configuration
@EnableJpaRepositories("com.otchi.domaine")
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableTransactionManagement
public class DatabaseConfig {

    private final String H2_SERVE_PORT = "8082";

    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("database/schema.sql")
                .addScript("database/data.sql")
                .build();
    }

    @Bean
    public JpaTransactionManager transactionManager() {
        return new JpaTransactionManager();
    }

    @Bean
    public HibernateJpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setDatabase(Database.H2);
        adapter.setShowSql(false);
        adapter.setGenerateDdl(false);
        return adapter;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource,JpaVendorAdapter jpaVendorAdapter) {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setPackagesToScan("com.otchi.domaine");
        emf.setDataSource(dataSource);
        emf.setJpaVendorAdapter(jpaVendorAdapter);
        return emf;
    }

    //FIXME Make severH2 available only in dev
//    @Bean
//    public Server serverH2() throws SQLException {
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