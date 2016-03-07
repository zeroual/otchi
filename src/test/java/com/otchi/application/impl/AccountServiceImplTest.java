package com.otchi.application.impl;

import com.otchi.application.AccountService;
import com.otchi.application.MailService;
import com.otchi.domaine.users.exceptions.AccountAlreadyExistsException;
import com.otchi.domaine.users.models.Account;
import com.otchi.domaine.users.models.AccountRepository;
import com.otchi.domaine.users.models.mocks.MockAccountRepository;
import com.otchi.utils.mocks.MockCrudRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;

public class AccountServiceImplTest {

    private AccountRepository accountRepository = new MockAccountRepository();
    private AccountService accountService;
    private String encryptedPassword = "$2a$10$9m.et2mcPSzA4RdS1AOGPemNLOCerTkyEC99BUdqMSNL.4F9HUtbW";
    private PasswordEncoder passwordEncoder = Mockito.mock(PasswordEncoder.class);
    private MailService mailService = Mockito.mock(MailService.class);

    @Before
    public void setUp() {
        MockCrudRepository.clearDatabase();
        Mockito.when(passwordEncoder.encode(anyString())).thenReturn(encryptedPassword);
        accountService = new AccountServiceImpl(accountRepository, passwordEncoder,mailService);

    }

    @Test
    public void shouldSaveAccount() throws AccountAlreadyExistsException {
        Account account = new Account("firstName", "lastName", "email", "password", "en");
        accountService.createAccount(account);
        Account newAccount = accountRepository.findOne(1L);
        assertThat(newAccount.getEmail()).isEqualTo("email");
        assertThat(newAccount.getUser().getFirstName()).isEqualTo("firstName");
        assertThat(newAccount.getUser().getLastName()).isEqualTo("lastName");
    }

    @Test
    public void shouldEnableTheAccountByDefault() throws AccountAlreadyExistsException {
        Account account = new Account("firstName", "lastName", "email", "password", "en");
        Account savedAccount = accountService.createAccount(account);
        assertThat(savedAccount.isEnabled()).isTrue();
    }


    @Test(expected = AccountAlreadyExistsException.class)
    public void shouldNotCreateAccountWithAlreadyExistingEmail() throws AccountAlreadyExistsException {
        Account oldAccount = new Account("firstName", "lastName", "emailAlreadyExist", "password", "en");
        accountService.createAccount(oldAccount);
        Account newAccount = new Account("firstName2", "lastName2", "emailAlreadyExist", "password2", "fr");
        accountService.createAccount(newAccount);
    }

    @Test
    public void shouldSetAccountDefaultLanguageToEnglishIfNotDefined() throws AccountAlreadyExistsException {
        Account account = new Account("firstName", "lastName", "email", "password", null);
        accountService.createAccount(account);
        Account newAccount = accountRepository.findOne(1L);
        assertThat(newAccount.getLangKey()).isEqualTo("en");

    }

    @Test
    public void shouldEncryptedAccountPassword() throws AccountAlreadyExistsException {
        Account account = new Account("firstName", "lastName", "email", "password", null);
        accountService.createAccount(account);
        Account newAccount = accountRepository.findOne(1L);
        assertThat(newAccount.getPassword()).isEqualTo(encryptedPassword);
    }

    @Test
    public void shouldSendWelcomeToNewUser() throws AccountAlreadyExistsException {
        Account account = new Account("firstName", "lastName", "email", "password", "en");
        Account savedAccount = accountService.createAccount(account);
        Mockito.verify(mailService,times(1)).sendWelcomeEmail(savedAccount.getUser());
    }
}