package com.otchi.api.facades.dto;

import com.otchi.domaine.users.models.User;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.Size;

public class UserDTO {

    @Email
    @Size(min = 5, max = 100)
    private String email;

    @Size(max = 50)
    private String firstName;

    @Size(max = 50)
    private String lastName;

    public UserDTO() {
    }

    public UserDTO(User user) {
        this(user.getEmail(), user.getFirstName(), user.getLastName());
    }

    public UserDTO(String email, String firstName, String lastName) {

        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }


    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                "}";
    }
}