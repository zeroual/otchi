package com.otchi.api.facades.dto;

import com.otchi.domain.users.models.User;

public class UserDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String picture;
    private String langKey;


    public UserDTO() {
    }

    public UserDTO(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.picture = user.picture();
        this.langKey = user.getLanguage();
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPicture() {
        return picture;
    }

    public String getLangKey() {
        return langKey;
    }
}