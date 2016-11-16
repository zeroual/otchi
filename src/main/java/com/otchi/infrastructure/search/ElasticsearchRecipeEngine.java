package com.otchi.infrastructure.search;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.otchi.domain.kitchen.Recipe;
import com.otchi.domain.search.RecipeDocument;
import com.otchi.domain.search.RecipeSearchEngine;
import com.otchi.domain.search.RecipeSearchResult;
import com.otchi.domain.search.RecipeSearchSuggestion;
import io.searchbox.client.JestClient;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

public class ElasticsearchRecipeEngine implements RecipeSearchEngine {

    private final JestClient jestClient;
    private static final String indexName = "otchi";
    private static final String typeName = "recipe";

    public ElasticsearchRecipeEngine(JestClient jestClient) {
        this.jestClient = jestClient;
    }

    @Override
    public void indexRecipe(Recipe recipe) {
        RecipeDocument recipeDocument = new RecipeDocument(recipe);
        String sourceJson = serializeToJson(recipeDocument);
        try {
            Index index = new Index.Builder(sourceJson)
                    .refresh(true)
                    .index(indexName)
                    .type(typeName)
                    .id(recipe.getId().toString())
                    .build();
            jestClient.execute(index);
        } catch (IOException e) {
            throw new RuntimeException("Oops: I can't communication with Elastic Search :(", e);
        }
    }

    @Override
    public List<RecipeSearchSuggestion> suggestRecipes(String query) {

        List<RecipeSearchSuggestion> suggestions = emptyList();
        String suggestQuery = prepareMatchQuery(query);
        Search search = new Search.Builder(suggestQuery)
                .addIndex(indexName)
                .addType(typeName)
                .build();
        try {
            SearchResult result = jestClient.execute(search);
            List<SearchResult.Hit<RecipeDocument, Void>> hits = result.getHits(RecipeDocument.class);
            suggestions = hits.stream()
                    .map(hit -> hit.source.getTitle())
                    .map(RecipeSearchSuggestion::new)
                    .collect(toList());
        } catch (IOException e) {
            throw new RuntimeException("Oops: I can't communication with Elastic Search :(", e);
        }

        return suggestions;
    }

    private String prepareMatchQuery(String query) {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery("title", query));
        return searchSourceBuilder.toString();
    }

    @Override
    public List<RecipeSearchResult> searchRecipes(String query) {
        List<RecipeSearchResult> recipeSearchResults = emptyList();
        String searchQuery = prepareMatchQuery(query);
        Search search = new Search.Builder(searchQuery)
                .addIndex(indexName)
                .build();

        try {
            SearchResult result = jestClient.execute(search);
            List<SearchResult.Hit<RecipeDocument, Void>> hits = result.getHits(RecipeDocument.class);
            recipeSearchResults = hits.stream()
                    .map(hit -> hit.source)
                    .map(recipe -> new RecipeSearchResult(recipe.getId(), recipe.getTitle(), recipe.getDescription(), recipe.getCookTime(), recipe.getPreparationTime()))
                    .collect(toList());
        } catch (IOException e) {
            throw new RuntimeException("Oops: I can't communication with Elastic Search :(", e);
        }
        return recipeSearchResults;
    }

    private String serializeToJson(RecipeDocument recipeDocument) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(recipeDocument);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Oops: I can't communication with Elastic Search :(", e);
        }
    }

}
