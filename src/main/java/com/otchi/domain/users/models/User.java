package com.otchi.domain.users.models;


import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "USERS")
public class User {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 50)
    @NotNull
    @Column(name = "FIRST_NAME", length = 50)
    private String firstName;

    @Size(max = 50)
    @NotNull
    @Column(name = "LAST_NAME", length = 50)
    private String lastName;

    @Email
    @Size(max = 100)
    @Column(name = "EMAIL", length = 100, unique = true)
    private String email;

    @Column(name = "LANG_KEY")
    @NotNull
    private String language;

    @Column(name = "PICTURE")
    private String picture;


    @NotNull
    @Size(min = 1, max = 100)
    @Column(length = 100, unique = true, nullable = false)
    private String username;

    private User() {
    }

    User(String username, String email, String firstName, String lastName, String language) {
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.language = language;
    }

    public User(String username) {
        this.username = username;
    }

    public Long getId() {
        return id;
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


    public String getUsername() {
        return username;
    }

    public String picture() {
        return this.picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getLanguage() {
        return language;
    }

    public void changeLanguageTo(String language) {
        this.language = language;
    }
}
