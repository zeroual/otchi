package com.otchi.infrastructure.config.elasticsearch;


import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.node.Node;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.io.File;
import java.net.URL;

import static com.otchi.infrastructure.config.Constants.SPRING_PROFILE_DEVELOPMENT;
import static org.apache.commons.io.FileUtils.readFileToString;
import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

@Configuration
@Profile(SPRING_PROFILE_DEVELOPMENT)
public class ElasticsearchDevConfig {


    @Value("${elasticsearch.index-settings-mapping}")
    private String indexSettingsAndMappings;

    @Value("${elasticsearch.index-name}")
    private String indexName;

    @Value("${elasticsearch.path-home}")
    private String pathHome;

    @Bean
    public Node node() {
        Settings settings = Settings.settingsBuilder()
                .put("path.home", pathHome)
                .build();
        Node node = nodeBuilder().clusterName(indexName)
                .local(true)
                .settings(settings).node();
        deleteIndexesIfAlreadyExist(node);
        createIndexes(node);
        return node;
    }

    private void deleteIndexesIfAlreadyExist(Node node) {
        IndicesExistsRequest request = new IndicesExistsRequest(indexName);
        Client client = node.client();
        if (client.admin().indices().exists(request).actionGet().isExists()) {
            DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest(indexName);
            client.admin().indices().delete(deleteIndexRequest).actionGet();
        }
    }

    private void createIndexes(Node node) {

        try {
            URL resource = getClass().getResource(indexSettingsAndMappings);
            String recipeMappingJson = readFileToString(new File(resource.toURI()));
            CreateIndexRequest indexRequest = new CreateIndexRequest(indexName).source(recipeMappingJson);
            node.client().admin().indices().create(indexRequest).actionGet();
        } catch (Exception e) {
            throw new RuntimeException("Ooops otchi can't find elasticsearch mapping files :( ", e);
        }
    }
}
