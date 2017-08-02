package com.otchi.api.facades.dto;

import com.otchi.domain.users.models.User;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDTO userDTO = (UserDTO) o;
        return Objects.equals(id, userDTO.id) &&
                Objects.equals(firstName, userDTO.firstName) &&
                Objects.equals(lastName, userDTO.lastName) &&
                Objects.equals(picture, userDTO.picture) &&
                Objects.equals(langKey, userDTO.langKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, picture, langKey);
    }
}