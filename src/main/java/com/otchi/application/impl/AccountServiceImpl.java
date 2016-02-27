package com.otchi.application.impl;


import com.otchi.application.AccountService;
import com.otchi.application.MailService;
import com.otchi.domaine.users.exceptions.AccountAlreadyExistsException;
import com.otchi.domaine.users.models.Account;
import com.otchi.domaine.users.models.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository, PasswordEncoder passwordEncoder, MailService mailService) {

        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.mailService = mailService;
    }

    @Override
    public Account createAccount(Account account) throws AccountAlreadyExistsException {
        String userEmail = account.getEmail();
        Optional<Account> accountOptional = accountRepository.findOneByEmail(userEmail);
        if (accountOptional.isPresent()) {
            throw new AccountAlreadyExistsException(account.getEmail());
        }
        if (account.getLangKey() == null) {
            account.setLangKey("en");
        }
        String encryptedPassword = passwordEncoder.encode(account.getPassword());
        account.setPassword(encryptedPassword);
        Account savedAccount = this.accountRepository.save(account);
        mailService.sendWelcomeEmail(savedAccount.getUser());
        return account;
    }
}
