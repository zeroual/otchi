package com.otchi.infrastructure.security;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.otchi.infrastructure.config.ResourcesPath;
import com.otchi.utils.AbstractIntegrationTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.Filter;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class AuthenticationIntegrationTest extends AbstractIntegrationTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private Filter springSecurityFilterChain;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .addFilter(springSecurityFilterChain)
                .build();
    }

    @Test
    @DatabaseSetup("/dbunit/users/users.xml")
    public void shouldAllowDatabaseLogin() throws Exception {
        mockMvc.perform(post(ResourcesPath.LOGIN).with(csrf())
                .param("email", "zeroual.abde@gmail.com")
                .param("password", "zeros")
        )
                .andExpect(status().isOk());
    }

    @Test
    @DatabaseSetup("/dbunit/users/users.xml")
    public void shouldRejectWrongPassword() throws Exception {
        mockMvc.perform(post(ResourcesPath.LOGIN).with(csrf())
                .param("email", "zeroual.abde@gmail.com")
                .param("password", "zeros2"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void shouldRejectAnonymousAccessToRestResources() throws Exception {
        mockMvc.perform(get(ResourcesPath.ME).with(csrf()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void shouldAllowUnAuthenticatedUsersToGetFeeds() throws Exception {
        mockMvc.perform(get(ResourcesPath.FEED).with(csrf()))
                .andExpect(status().isOk());

    }

    @Test
    public void shouldDenyAnyPostRequestIntoFeedApiOfUnAuthenticatedUsers() throws Exception {
        mockMvc.perform(post(ResourcesPath.FEED).with(csrf()))
                .andExpect(status().isUnauthorized());

    }


    @Test
    @WithMockUser
    public void shouldAllowLogout() throws Exception {
        mockMvc.perform(post(ResourcesPath.LOGOUT).with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldAllowAnonymousAccessToWebResources() throws Exception {
        mockMvc.perform(get("/index.html"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldAllowUserToRegister() throws Exception {
        mockMvc.perform(post(ResourcesPath.REGISTER).with(csrf()))
                .andExpect(status().isBadRequest());
    }
}
