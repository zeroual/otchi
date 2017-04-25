package com.otchi.domain.users.models;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static com.otchi.domain.users.models.UserBuilder.asUser;

@Entity
@Table(name = "ACCOUNT")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "PASSWORD")
    private String password;

    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "USERNAME", length = 100, unique = true, nullable = false)
    private String username;

    @Column(name = "ENABLED", nullable = false)
    private boolean enabled = true;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "USER_ID")
    @NotNull
    private User user;

    private Account() {
    }

    public Account(String firstName, String lastName, String email, String password, String langKey) {
        this(firstName, lastName, email, email, password, langKey);
    }

    public Account(String firstName, String lastName, String email, String username, String password, String langKey) {
        this.password = password;
        this.username = username;
        this.user = asUser()
                .withUsername(username)
                .withLastName(lastName)
                .withFirstName(firstName)
                .withEmail(email)
                .withLanguage(langKey)
                .build();

    }


    public Long getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public User getUser() {
        return user;
    }

    public String getLangKey() {
        return this.user.getLanguage();
    }

    public void changeLanguageTo(String langKey) {
        this.user.changeLanguageTo(langKey);
    }

    public String getUsername() {
        return username;
    }

    public void setPicture(String picture) {
        this.user.setPicture(picture);
    }
}
