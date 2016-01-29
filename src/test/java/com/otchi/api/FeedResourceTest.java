package com.otchi.api;


import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.otchi.infrastructure.config.ResourcesPath;
import com.otchi.utils.AbstractControllerTest;
import org.junit.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class FeedResourceTest extends AbstractControllerTest {

    //FIXME : i guess if we write integration test with cucumber will be better
    @Test
    @DatabaseSetup("/dbunit/social/stream-feeds.xml")
    public void shouldFetchAllRecipes() throws Exception {
        mockMvc.perform(get(ResourcesPath.FEED)
                .contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":1,\"author\":{\"id\":1,\"firstName\":\"Abdellah\",\"lastName\":\"ZEROUAL\"},\"postingDate\":\"2015-02-28 00:00:00\",\"content\":{\"type\":\"RECIPE\",\"id\":2,\"description\":null,\"cookTime\":null,\"preparationTime\":null,\"ingredients\":[],\"instructions\":[],\"title\":\"TITLE_SAMPLE_2\"}}]"));
    }
}