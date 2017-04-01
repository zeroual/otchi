package com.otchi.api;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.otchi.infrastructure.config.ResourcesPath;
import com.otchi.utils.AbstractIntegrationTest;
import org.junit.Test;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CurrentUserResourceTest extends AbstractIntegrationTest {

    @Test
    @DatabaseSetup("/dbunit/users/users.xml")
    public void shouldReturnAccountInfoOfAuthenticatedUser() throws Exception {
        mockMvc.perform(get(ResourcesPath.ME).with(user("zeroual.abde@gmail.com"))
                .contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1,\"firstName\":\"Abdellah\",\"lastName\":\"ZEROUAL\",\"picture\":\"http://host.com/image2.png\",\"langKey\":\"fr\"}"));
    }

    @Test
    @DatabaseSetup("/dbunit/users/users.xml")
    public void shouldReturnBadRequestIfTheCurrentUserIsNotFound() throws Exception {
        mockMvc.perform(get(ResourcesPath.ME).with(user("toto@gmail.com"))
                .contentType(contentType))
                .andExpect(status().isBadRequest());
    }


}
