package com.otchi.api;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.otchi.infrastructure.config.ResourcesPath;
import com.otchi.utils.AbstractIntegrationTest;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RecommendationsResourceTest extends AbstractIntegrationTest {

    @Test
    @DatabaseSetup("/dbunit/users/users.xml")
    @Transactional
    public void fetchFollowingsRecommendation() throws Exception {
        mockMvc.perform(get(ResourcesPath.RECOMMENDATIONS + "/followings")
                .with(user("zeroual.abde@gmail.com"))
                .with(csrf())
                .contentType(contentType))
                .andExpect(content().json("[{\"id\":2,\"firstName\":\"Reda\",\"lastName\":\"JAIFAR\"}]"))
                .andExpect(status().isOk());

    }

}
