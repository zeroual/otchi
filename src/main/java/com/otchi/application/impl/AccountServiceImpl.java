package com.otchi.application.impl;


import com.otchi.application.AccountService;
import com.otchi.application.MailService;
import com.otchi.domain.users.exceptions.AccountAlreadyExistsException;
import com.otchi.domain.users.models.Account;
import com.otchi.domain.users.models.AccountRepository;
import com.otchi.infrastructure.storage.BlobStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;
    private final BlobStorageService blobStorageService;
    private final String defaultUserPicture;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository,
                              PasswordEncoder passwordEncoder,
                              MailService mailService,
                              BlobStorageService blobStorageService,
                              @Value("${otchi.user.default-picture}") String defaultUserPicture) {

        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.mailService = mailService;
        this.blobStorageService = blobStorageService;
        this.defaultUserPicture = defaultUserPicture;
    }

    @Override
    public Account createAccount(Account account, Optional<File> picture) throws AccountAlreadyExistsException {
        String username = account.getUsername();
        Optional<Account> accountOptional = accountRepository.findOneByUsername(username);
        if (accountOptional.isPresent()) {
            throw new AccountAlreadyExistsException(account.getUsername());
        }
        if (account.getLangKey() == null) {
            account.changeLanguageTo("en");
        }
        String encryptedPassword = passwordEncoder.encode(account.getPassword());
        account.setPassword(encryptedPassword);

        if (picture.isPresent()) {
            File file = picture.get();
            String pictureURL = blobStorageService.save(file);
            account.setPicture(pictureURL);
        } else {
            account.setPicture(defaultUserPicture);
        }

        Account savedAccount = this.accountRepository.save(account);
        mailService.sendWelcomeEmail(savedAccount.getUser());
        return account;
    }
}
