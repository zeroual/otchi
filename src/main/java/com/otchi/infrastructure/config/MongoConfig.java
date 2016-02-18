package com.otchi.infrastructure.config;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * Created by MJR2 on 2/15/2016.
 */

@Configuration
@EnableMongoRepositories(basePackages = "com.otchi.domaine")
public class MongoConfig extends AbstractMongoConfiguration{


    @Override
    protected String getDatabaseName() {
        return "otchiDB";
    }

    @Override
    public Mongo mongo() throws Exception {
        return new MongoClient();
    }
}
