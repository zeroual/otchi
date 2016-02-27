package com.otchi.api;

import com.otchi.api.facades.dto.AccountDTO;
import com.otchi.application.AccountService;
import com.otchi.domaine.users.exceptions.AccountAlreadyExistsException;
import com.otchi.domaine.users.models.Account;
import com.otchi.infrastructure.config.ResourcesPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class AccountResource {

    private final Logger log = LoggerFactory.getLogger(AccountResource.class);

    @Autowired
    private AccountService accountService;

    @RequestMapping(value = ResourcesPath.REGISTER, method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void registerAccount(@RequestBody @Valid AccountDTO accountDTO) throws AccountAlreadyExistsException {
        Account account = accountDTO.toDomain();
        accountService.createAccount(account);
    }

}
