package com.otchi.api.facades.dto;

import com.otchi.domain.users.models.User;

public class UserDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String picture;


    public UserDTO() {
    }

    public UserDTO(User user) {
        this(user.getId(), user.getFirstName(), user.getLastName());
        this.picture = user.picture();
    }

    public UserDTO(Long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
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

    @Override
    public String toString() {
        return "UserDTO{" +
                ", id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                "}";
    }

}