package com.otchi.domain.users.models;

public class UserBuilder {

    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String language;
    private String picture;

    public static UserBuilder asUser() {
        return new UserBuilder();
    }


    public UserBuilder withUsername(String username) {
        this.username = username;
        return this;
    }

    public UserBuilder withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public UserBuilder withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public UserBuilder withLanguage(String language) {
        this.language = language;
        return this;
    }

    public UserBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public User build() {
        User user = new User(username, email, firstName, lastName, language);
        user.setPicture(picture);
        return user;
    }

    public UserBuilder withPicture(String picture) {
        this.picture = picture;
        return this;
    }
}
