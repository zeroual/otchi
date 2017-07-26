package com.otchi.api;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.otchi.utils.AbstractIntegrationTest;
import org.junit.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class TodayRecipeControllerIntegrationTest extends AbstractIntegrationTest {

    @Test
    @DatabaseSetup("/dbunit/social/today-recipe.xml")
    public void shouldGetTodayRecipe() throws Exception {

        mockMvc.perform(get("/api/v1/today-recipe"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"postId\":24,\"image\":\"image.jpg\",\"title\":\"7 Light and Bright Summer Dishes\",\"chef\":{\"id\":1,\"firstName\":\"Abdellah\",\"lastName\":\"ZEROUAL\",\"picture\":\"picture1.jpg\"}}"));
    }
}
