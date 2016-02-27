package com.otchi.application;

import com.otchi.domaine.users.exceptions.AccountAlreadyExistsException;
import com.otchi.domaine.users.models.Account;

public interface AccountService {

    Account createAccount(Account account) throws AccountAlreadyExistsException;

}
