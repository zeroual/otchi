package com.otchi.api.facades.dto;

import com.otchi.domain.users.models.Account;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class AccountDTO implements DTO<Account> {

    @NotNull
    @Size(min = 8, max = 100)
    private String password;

    @Size(min = 2, max = 50)
    @NotNull
    private String firstName;

    @NotNull
    @Size(min = 2, max = 50)
    private String lastName;

    @NotNull
    @Email
    @Size(min = 5, max = 100)
    private String email;

    @Size(min = 2, max = 5)
    private String langKey;

    private AccountDTO() {

    }

    public AccountDTO(String firstName, String lastName, String email, String password, String langKey) {
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.langKey = langKey;
    }

    public String getLangKey() {
        return langKey;
    }

    public void setLangKey(String langKey) {
        this.langKey = langKey;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public Account toDomain() {
        return new Account(firstName, lastName, email, password, langKey);
    }

    @Override
    public void extractFromDomain(Account model) {

    }
}
