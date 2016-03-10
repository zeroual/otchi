package com.otchi.application;

import com.otchi.domain.users.exceptions.AccountAlreadyExistsException;
import com.otchi.domain.users.models.Account;

public interface AccountService {

    Account createAccount(Account account) throws AccountAlreadyExistsException;

}
