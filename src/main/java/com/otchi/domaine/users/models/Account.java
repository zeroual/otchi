package com.otchi.domaine.users.models;


import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Account {
    @Id
    private Long id;

    @NotNull
    @Size(min = 60, max = 60)
    @Column(name = "PASSWORD", length = 60)
    private String password;

    @Email
    @Size(max = 100)
    @Column(name = "EMAIL", length = 100, unique = true)
    private String email;

    @Column(name = "ACTIVATED", nullable = false)
    private boolean activated = false;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

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

    public boolean isActivated() {
        return activated;
    }

    public User getUser() {
        return user;
    }
}
