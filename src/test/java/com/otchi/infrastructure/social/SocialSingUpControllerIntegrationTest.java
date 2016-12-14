package com.otchi.infrastructure.social;

import com.otchi.application.AccountService;
import com.otchi.domain.users.models.Account;
import com.otchi.domain.users.models.AccountRepository;
import com.otchi.infrastructure.config.SocialConfig;
import com.otchi.utils.AbstractIntegrationTest;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.*;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

public class SocialSingUpControllerIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountRepository accountRepository;

    @Mock
    private ProviderSignInUtils providerSignInUtilsMock;

    @Mock
    private UsersConnectionRepository usersConnectionRepositoryMock;

    private ConnectionRepository connectionRepository = Mockito.mock(ConnectionRepository.class);

    private Connection connection = Mockito.mock(Connection.class);

    @Autowired
    private SocialAccountService socialAccountService;

    @Autowired
    private SocialSingUpController socialSingUpController;

    public void setUpMocks() {
        MockitoAnnotations.initMocks(this);
        when(providerSignInUtilsMock.getConnectionFromSession(any())).thenReturn(connection);
        UserProfile userProfile = new UserProfile("id", "name", "firs   tName", "lastName", "email@domaine.com", null);

        when(connection.fetchUserProfile()).thenReturn(userProfile);
        when(connection.getKey()).thenReturn(new ConnectionKey("facebook", "1197927"));
        when(usersConnectionRepositoryMock.createConnectionRepository(any())).thenReturn(connectionRepository);

        ReflectionTestUtils.setField(socialSingUpController, "providerSignInUtils", providerSignInUtilsMock);
        ReflectionTestUtils.setField(socialAccountService, "usersConnectionRepository", usersConnectionRepositoryMock);
    }

    @Test
    public void shouldRedirectToErrorPageIfConnectionIsNull() throws Exception {
        setUpMocks();
        when(providerSignInUtilsMock.getConnectionFromSession(any())).thenReturn(null);
        mockMvc.perform(get(SocialConfig.SOCIAL_SIGN_UP_URL)
                .contentType(contentType))
                .andExpect(redirectedUrl("/#/social-register/null?success=false"));
    }

    @Test
    public void shouldRedirectToErrorPageIfUsernameAlreadyExist() throws Exception {
        setUpMocks();
        Account account = new Account("firstName", "lastName", "email@domaine.com", "password", "en");
        accountService.createAccount(account, Optional.empty());

        mockMvc.perform(get(SocialConfig.SOCIAL_SIGN_UP_URL)
                .contentType(contentType))
                .andExpect(redirectedUrl("/#/social-register/facebook?success=false&error=ACCOUNT_ALREADY_EXIST"));
    }

    @Test
    public void shouldCreateSocialUserIfNotExist() throws Exception {
        setUpMocks();
        Account account = new Account("firstName", "lastName", "new_email@domaine.com", "password", "en");
        accountService.createAccount(account, Optional.empty());
        mockMvc.perform(get(SocialConfig.SOCIAL_SIGN_UP_URL)
                .contentType(contentType))
                .andExpect(redirectedUrl("/#/social-register/facebook?success=true"));

        assertThat(accountRepository.findOneByUsername("new_email@domaine.com")).isPresent();
        verify(connectionRepository, times(1)).addConnection(connection);

    }
}
