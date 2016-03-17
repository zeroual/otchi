package com.otchi.infrastructure.social;

import com.otchi.application.AccountService;
import com.otchi.domain.users.exceptions.AccountAlreadyExistsException;
import com.otchi.domain.users.models.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.SecureRandom;

@Service
public class SocialAccountService {

    private final UsersConnectionRepository usersConnectionRepository;
    private final AccountService accountService;

    @Autowired
    public SocialAccountService(AccountService accountService, UsersConnectionRepository usersConnectionRepository) {
        this.usersConnectionRepository = usersConnectionRepository;
        this.accountService = accountService;
    }

    public void createAccount(Connection<?> connection, String langKey) throws AccountAlreadyExistsException {
        UserProfile userProfile = connection.fetchUserProfile();
        String providerId = connection.getKey().getProviderId();
        Account account = this.generateAccountFrom(userProfile, langKey, providerId);
        accountService.createAccount(account);
        this.saveSocialConnection(account.getUsername(), connection);
    }

    private Account generateAccountFrom(UserProfile userProfile, String langKey, String providerId) {
        String email = userProfile.getEmail();
        String username = email;
        if ("twitter".equals(providerId)) {
            username = userProfile.getUsername();
        }
        String temporalPassword = generateRandomPassword();
        Account account = new Account(userProfile.getFirstName(), userProfile.getLastName(), email, username, temporalPassword, langKey);
        return account;
    }

    private String generateRandomPassword() {
        SecureRandom random = new SecureRandom();
        String temporalPassword = new BigInteger(130, random).toString(32);
        return temporalPassword;
    }

    private void saveSocialConnection(String login, Connection<?> connection) {
        ConnectionRepository connectionRepository = usersConnectionRepository.createConnectionRepository(login);
        connectionRepository.addConnection(connection);
    }
}
