package com.otchi.domaine.users.models;


import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "PASSWORD")
    private String password;

    @Email
    @Column(name = "EMAIL", length = 100, unique = true)
    private String email;

    @Column(name = "ENABLED", nullable = false)
    private boolean enabled = true;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "USER_ID")
    @NotNull
    private User user;

    @Column(name = "LANG_KEY")
    @NotNull
    private String langKey;

    public Account(String firstName, String lastName, String email, String password, String langKey) {
        this.password = password;
        this.email = email;
        this.langKey = langKey;
        this.user = new User(email, firstName, lastName);
    }

    private Account() {
    }


    public Long getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
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
}
