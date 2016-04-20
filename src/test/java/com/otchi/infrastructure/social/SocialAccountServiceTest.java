package com.otchi.infrastructure.social;

import com.otchi.application.AccountService;
import com.otchi.domain.users.exceptions.AccountAlreadyExistsException;
import com.otchi.domain.users.models.Account;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.social.connect.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class SocialAccountServiceTest {

    private SocialAccountService socialAccountService;
    private AccountService accountService = Mockito.mock(AccountService.class);
    private UsersConnectionRepository usersConnectionRepository = Mockito.mock(UsersConnectionRepository.class);
    private Connection connection = Mockito.mock(Connection.class);
    private ConnectionRepository connectionRepository = Mockito.mock(ConnectionRepository.class);

    @Before
    public void setUp() {
        socialAccountService = new SocialAccountService(accountService, usersConnectionRepository);
        UserProfile userProfile = new UserProfile("id", "name", "firstName", "lastName", "email", null);
        when(connection.getKey()).thenReturn(new ConnectionKey("providerId", "1828823"));
        when(connection.fetchUserProfile()).thenReturn(userProfile);
        when(usersConnectionRepository.createConnectionRepository(anyString())).thenReturn(connectionRepository);
    }

    @Test
    public void shouldGetAccountInformationFormSocialConnectionAndCallAccountServiceToCreateLocalAccount()
            throws AccountAlreadyExistsException {
        socialAccountService.createAccount(connection, "fr");
        ArgumentCaptor<Account> accountCaptor = ArgumentCaptor.forClass(Account.class);
        verify(accountService, times(1)).createAccount(accountCaptor.capture());
        Account capturedAccount = accountCaptor.getValue();
        assertThat(capturedAccount.getUsername()).isEqualTo("email");
        assertThat(capturedAccount.getLangKey()).isEqualTo("fr");
        assertThat(capturedAccount.getPassword()).isNotNull().isNotEmpty();

        assertThat(capturedAccount.getUser().getFirstName()).isEqualTo("firstName");
        assertThat(capturedAccount.getUser().getLastName()).isEqualTo("lastName");
        assertThat(capturedAccount.getUser().getUsername()).isEqualTo("email");
        assertThat(capturedAccount.getUser().getEmail()).isEqualTo("email");

    }

    @Test
    public void shouldUseTwitterUsernameAsLocalUsernameIfTwitterIsTheProvider() throws AccountAlreadyExistsException {
        UserProfile userProfile = new UserProfile("id", "name", "firstName", "lastName", null, "username");
        when(connection.getKey()).thenReturn(new ConnectionKey("twitter", "1828823"));
        when(connection.fetchUserProfile()).thenReturn(userProfile);

        socialAccountService.createAccount(connection, "fr");
        ArgumentCaptor<Account> accountCaptor = ArgumentCaptor.forClass(Account.class);
        verify(accountService, times(1)).createAccount(accountCaptor.capture());
        Account capturedAccount = accountCaptor.getValue();
        assertThat(capturedAccount.getUsername()).isEqualTo("username");
        assertThat(capturedAccount.getUser().getEmail()).isNull();
    }


    @Test
    public void shouldUseEmailAsLocalUsernameIfFacebookIsTheProvider() throws AccountAlreadyExistsException {
        UserProfile userProfile = new UserProfile("id", "name", "firstName", "lastName", "email", null);
        when(connection.getKey()).thenReturn(new ConnectionKey("facebook", "1828823"));
        when(connection.fetchUserProfile()).thenReturn(userProfile);

        socialAccountService.createAccount(connection, "fr");
        ArgumentCaptor<Account> accountCaptor = ArgumentCaptor.forClass(Account.class);
        verify(accountService, times(1)).createAccount(accountCaptor.capture());
        Account capturedAccount = accountCaptor.getValue();
        assertThat(capturedAccount.getUsername()).isEqualTo("email");
        assertThat(capturedAccount.getUser().getEmail()).isEqualTo("email");
    }

    @Test
    public void shouldUseEmailAsLocalUsernameIfGoogleIsTheProvider() throws AccountAlreadyExistsException {
        UserProfile userProfile = new UserProfile("id", "name", "firstName", "lastName", "email", null);
        when(connection.getKey()).thenReturn(new ConnectionKey("google", "1828823"));
        when(connection.fetchUserProfile()).thenReturn(userProfile);

        socialAccountService.createAccount(connection, "fr");
        ArgumentCaptor<Account> accountCaptor = ArgumentCaptor.forClass(Account.class);
        verify(accountService, times(1)).createAccount(accountCaptor.capture());
        Account capturedAccount = accountCaptor.getValue();
        assertThat(capturedAccount.getUsername()).isEqualTo("email");
        assertThat(capturedAccount.getUser().getEmail()).isEqualTo("email");
    }


    @Test
    public void shouldSaveUserConnectionWhenCreatingLocalAccount() throws AccountAlreadyExistsException {
        socialAccountService.createAccount(connection, "fr");
        verify(connectionRepository, times(1)).addConnection(connection);
    }

    @Test(expected = AccountAlreadyExistsException.class)
    public void shouldThrowExceptionIfAlreadyExistLocalAccountWithSameUsernameOrEmail()
            throws AccountAlreadyExistsException {
        when(accountService.createAccount(anyObject())).thenThrow(new AccountAlreadyExistsException(""));
        socialAccountService.createAccount(connection, "fr");
    }

}