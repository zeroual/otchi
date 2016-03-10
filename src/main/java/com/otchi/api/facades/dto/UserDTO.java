package com.otchi.api.facades.dto;

import com.otchi.domain.users.models.User;

public class UserDTO {

    private Long id;
    private String firstName;
    private String lastName;


    public UserDTO() {
    }

    public UserDTO(User user) {
        this(user.getId(), user.getFirstName(), user.getLastName());
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

    @Override
    public String toString() {
        return "UserDTO{" +
                ", id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                "}";
    }

}