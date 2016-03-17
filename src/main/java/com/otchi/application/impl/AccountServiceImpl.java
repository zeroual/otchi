package com.otchi.application.impl;


import com.otchi.application.AccountService;
import com.otchi.application.MailService;
import com.otchi.domain.users.exceptions.AccountAlreadyExistsException;
import com.otchi.domain.users.models.Account;
import com.otchi.domain.users.models.AccountRepository;
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
        String username = account.getUsername();
        Optional<Account> accountOptional = accountRepository.findOneByUsername(username);
        if (accountOptional.isPresent()) {
            throw new AccountAlreadyExistsException(account.getUsername());
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
