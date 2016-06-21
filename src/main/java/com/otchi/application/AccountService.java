package com.otchi.application;

import com.otchi.domain.users.exceptions.AccountAlreadyExistsException;
import com.otchi.domain.users.models.Account;

import java.io.File;
import java.util.Optional;

public interface AccountService {

    Account createAccount(Account account, Optional<File> picture) throws AccountAlreadyExistsException;
}
