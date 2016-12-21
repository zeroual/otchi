package com.otchi.infrastructure.security;

import api.stepsDefinition.IntegrationTestsConfig;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.otchi.infrastructure.boot.OtchiApplicationStarter;
import com.otchi.infrastructure.config.ResourcesPath;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.Filter;

import static com.otchi.infrastructure.config.Constants.SPRING_PROFILE_DEVELOPMENT;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {OtchiApplicationStarter.class, IntegrationTestsConfig.class},
        initializers = ConfigFileApplicationContextInitializer.class)
@WebAppConfiguration()
@IntegrationTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@ActiveProfiles(SPRING_PROFILE_DEVELOPMENT)
public class AuthenticationIntegrationTest {

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
        mockMvc.perform(get(ResourcesPath.FEED).with(csrf()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DatabaseSetup("/dbunit/users/users.xml")
    public void shouldAllowAuthenticatedAccessToRestResources() throws Exception {
        mockMvc.perform(get(ResourcesPath.FEED)
                .with(user("zeroual.abde@gmail.com").password("zeros")).with(csrf()))
                .andExpect(status().isOk());
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
