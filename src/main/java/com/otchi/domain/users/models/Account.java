package com.otchi.domain.users.models;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
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

    @Column(name = "LANG_KEY")
    @NotNull
    private String langKey;

    private Account() {
    }

    public Account(String firstName, String lastName, String email, String password, String langKey) {
        this(firstName, lastName, email, email, password, langKey);
    }

    public Account(String firstName, String lastName, String email, String username, String password, String langKey) {
        this.password = password;
        this.username = username;
        this.langKey = langKey;
        this.user = new User(username, email, firstName, lastName);
    }


    public Long getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }


    public boolean isEnabled() {
        return enabled;
    }

    public User getUser() {
        return user;
    }

    public String getLangKey() {
        return langKey;
    }

    public void setLangKey(String langKey) {
        this.langKey = langKey;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setPicture(String picture) {
        this.user.setPicture(picture);
    }
}
