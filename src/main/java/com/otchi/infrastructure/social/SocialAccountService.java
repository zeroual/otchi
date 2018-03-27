package com.otchi.infrastructure.social;

import com.otchi.application.AccountService;
import com.otchi.domain.users.exceptions.AccountAlreadyExistsException;
import com.otchi.domain.users.models.Account;
import com.otchi.infrastructure.utils.FileUtilsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.stereotype.Service;

import java.io.File;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.SecureRandom;
import java.util.Optional;

import static java.util.Optional.of;

@Service
public class SocialAccountService {

    private final UsersConnectionRepository usersConnectionRepository;
    private final AccountService accountService;
    private FileUtilsServiceImpl fileUtilsService;

    private final Logger log = LoggerFactory.getLogger(SocialAccountService.class);

    @Autowired
    public SocialAccountService(AccountService accountService,
                                UsersConnectionRepository usersConnectionRepository,
                                FileUtilsServiceImpl fileUtilsService) {
        this.usersConnectionRepository = usersConnectionRepository;
        this.accountService = accountService;
        this.fileUtilsService = fileUtilsService;
    }

    public void createAccount(Connection<?> connection, String langKey) throws AccountAlreadyExistsException {
        UserProfile userProfile = connection.fetchUserProfile();
        String providerId = connection.getKey().getProviderId();
        Account account = this.generateAccountFrom(userProfile, langKey, providerId);

        Optional<File> socialPicture = Optional.empty();
        if (connection.getImageUrl() != null && !connection.getImageUrl().isEmpty()) {
            try {
                URL pictureURL = new URL(connection.getImageUrl());
                socialPicture = of(fileUtilsService.getFileFrom(pictureURL));
            } catch (MalformedURLException e) {
                log.error("social picture url is mal formed {}", connection.getImageUrl());
            }
        }
        accountService.createAccount(account, socialPicture);
        this.saveSocialConnection(account.getUsername(), connection);
        removeTemporaryFileCreatedFromURL(socialPicture);
    }

    private void removeTemporaryFileCreatedFromURL(Optional<File> file) {
        if (file.isPresent()) {
            file.get().delete();
        }
    }


    private Account generateAccountFrom(UserProfile userProfile, String langKey, String providerId) {
        String email = userProfile.getEmail();
        String username = email;
        if ("twitter".equals(providerId)) {
            username = userProfile.getUsername();
        }
        String temporalPassword = generateRandomPassword();
        return new Account(userProfile.getFirstName(), userProfile.getLastName(), email, username, temporalPassword, langKey);
    }

    private String generateRandomPassword() {
        SecureRandom random = new SecureRandom();
        return new BigInteger(130, random).toString(32);
    }

    private void saveSocialConnection(String login, Connection<?> connection) {
        ConnectionRepository connectionRepository = usersConnectionRepository.createConnectionRepository(login);
        connectionRepository.addConnection(connection);
    }
}
