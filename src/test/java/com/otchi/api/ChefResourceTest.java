package com.otchi.api;


import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.otchi.utils.AbstractIntegrationTest;
import org.junit.Test;

import static com.otchi.infrastructure.config.ResourcesPath.CHEF;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ChefResourceTest extends AbstractIntegrationTest {

    @Test
    @DatabaseSetup("/dbunit/social/publications.xml")
    public void shouldGetChefInformation() throws Exception {
        mockMvc.perform(get(CHEF + "/1"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    @DatabaseSetup("/dbunit/social/publications.xml")
    public void shouldGet404WhenUserIsNotExists() throws Exception {
        mockMvc.perform(get(CHEF + "/2938"))
                .andExpect(status().isNotFound())
                .andReturn();
    }
}
