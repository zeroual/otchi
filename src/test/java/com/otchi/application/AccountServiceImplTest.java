package com.otchi.application;

import com.otchi.application.impl.AccountServiceImpl;
import com.otchi.domain.users.exceptions.AccountAlreadyExistsException;
import com.otchi.domain.users.models.Account;
import com.otchi.domain.users.models.AccountRepository;
import com.otchi.domain.users.models.mocks.MockAccountRepository;
import com.otchi.infrastructure.storage.BlobStorageService;
import com.otchi.utils.mocks.MockCrudRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.File;
import java.net.MalformedURLException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

public class AccountServiceImplTest {

    private AccountRepository accountRepository = new MockAccountRepository();
    private AccountService accountService;
    private String encryptedPassword = "$2a$10$9m.et2mcPSzA4RdS1AOGPemNLOCerTkyEC99BUdqMSNL.4F9HUtbW";
    private PasswordEncoder passwordEncoder = Mockito.mock(PasswordEncoder.class);
    private MailService mailService = Mockito.mock(MailService.class);
    private BlobStorageService blobStorageService = Mockito.mock(BlobStorageService.class);
    private String DEFAULT_USER_PICTURE = "http://otchi.com/images/default.png";
    private Optional<File> NO_PICTURE = Optional.empty();

    @Before
    public void setUp() {
        MockCrudRepository.clearDatabase();
        Mockito.when(passwordEncoder.encode(anyString())).thenReturn(encryptedPassword);
        accountService = new AccountServiceImpl(accountRepository, passwordEncoder, mailService, blobStorageService, DEFAULT_USER_PICTURE);

    }

    @Test
    public void shouldSaveAccount() throws AccountAlreadyExistsException {
        Account account = new Account("firstName", "lastName", "email", "password", "en");
        accountService.createAccount(account, NO_PICTURE);
        Account newAccount = accountRepository.findOne(1L);
        assertThat(newAccount.getUsername()).isEqualTo("email");
        assertThat(newAccount.getUser().getFirstName()).isEqualTo("firstName");
        assertThat(newAccount.getUser().getLastName()).isEqualTo("lastName");
    }

    @Test
    public void shouldEnableTheAccountByDefault() throws AccountAlreadyExistsException {
        Account account = new Account("firstName", "lastName", "email", "password", "en");
        Account savedAccount = accountService.createAccount(account, NO_PICTURE);
        assertThat(savedAccount.isEnabled()).isTrue();
    }


    @Test(expected = AccountAlreadyExistsException.class)
    public void shouldNotCreateAccountWithAlreadyExistingEmail() throws AccountAlreadyExistsException {
        Account oldAccount = new Account("firstName", "lastName", "emailAlreadyExist", "password", "en");
        accountService.createAccount(oldAccount, NO_PICTURE);
        Account newAccount = new Account("firstName2", "lastName2", "emailAlreadyExist", "password2", "fr");
        accountService.createAccount(newAccount, NO_PICTURE);
    }

    @Test(expected = AccountAlreadyExistsException.class)
    public void shouldNotCreateAccountWithAlreadyExistingUsername() throws AccountAlreadyExistsException {
        Account oldAccount = new Account("firstName", "lastName", null, "usernameAlreadyExist", "password", "en");
        accountService.createAccount(oldAccount, NO_PICTURE);
        Account newAccount = new Account("firstName2", "lastName2", null, "usernameAlreadyExist", "password2", "fr");
        accountService.createAccount(newAccount, NO_PICTURE);
    }

    @Test
    public void shouldSetAccountDefaultLanguageToEnglishIfNotDefined() throws AccountAlreadyExistsException {
        Account account = new Account("firstName", "lastName", "email", "password", null);
        accountService.createAccount(account, NO_PICTURE);
        Account newAccount = accountRepository.findOne(1L);
        assertThat(newAccount.getLangKey()).isEqualTo("en");

    }

    @Test
    public void shouldEncryptedAccountPassword() throws AccountAlreadyExistsException {
        Account account = new Account("firstName", "lastName", "email", "password", null);
        accountService.createAccount(account, NO_PICTURE);
        Account newAccount = accountRepository.findOne(1L);
        assertThat(newAccount.getPassword()).isEqualTo(encryptedPassword);
    }

    @Test
    public void shouldSendWelcomeToNewUser() throws AccountAlreadyExistsException {
        Account account = new Account("firstName", "lastName", "email", "password", "en");
        Account savedAccount = accountService.createAccount(account, NO_PICTURE);
        Mockito.verify(mailService, times(1)).sendWelcomeEmail(savedAccount.getUser());
    }

    @Test
    public void shouldAssignDefaultPictureToUserIfNotProvided() throws AccountAlreadyExistsException {
        Account account = new Account("firstName", "lastName", "email", "password", null);
        accountService.createAccount(account, NO_PICTURE);
        Account newAccount = accountRepository.findOne(1L);
        assertThat(newAccount.getUser().picture()).isEqualTo(DEFAULT_USER_PICTURE);
    }

    @Test
    public void shouldAssignPictureToUserAndSaveIt() throws AccountAlreadyExistsException, MalformedURLException {
        Account account = new Account("firstName", "lastName", "email", "password", "en");
        File userPictureFile = new File("user picture");

        String userPictureURL = "http://storage.com/19822829.png";
        when(blobStorageService.save(userPictureFile)).thenReturn(userPictureURL);
        accountService.createAccount(account, Optional.of(userPictureFile));
        Account newAccount = accountRepository.findOne(1L);
        assertThat(newAccount.getUser().picture()).isEqualTo(userPictureURL);
    }

}