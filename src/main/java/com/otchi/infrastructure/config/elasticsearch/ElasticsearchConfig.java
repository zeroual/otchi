package com.otchi.infrastructure.config.elasticsearch;

import com.otchi.domain.search.RecipeSearchEngine;
import com.otchi.infrastructure.search.ElasticsearchRecipeEngine;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.io.IOException;
import java.net.URISyntaxException;

@Configuration
@Import({ElasticsearchDevConfig.class})
public class ElasticsearchConfig {

    @Value("${elasticsearch.hostname}")
    private String hostname;

    @Bean
    public RecipeSearchEngine searchEngine(JestClient jestClient) throws URISyntaxException, IOException {
        return new ElasticsearchRecipeEngine(jestClient);
    }

    @Bean
    public JestClient jestClient() {
        JestClientFactory factory = new JestClientFactory();
        factory.setHttpClientConfig(new HttpClientConfig
                .Builder(hostname)
                .multiThreaded(true)
                .build());
        JestClient client = factory.getObject();
        return client;
    }
}
