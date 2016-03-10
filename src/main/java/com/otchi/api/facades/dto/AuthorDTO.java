package com.otchi.api.facades.dto;


import com.otchi.domain.users.models.User;

public class AuthorDTO {
    private Long id;
    private String firstName;
    private String lastName;


    public AuthorDTO(User author) {
        this.id = author.getId();
        this.firstName = author.getFirstName();
        this.lastName = author.getLastName();
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
}
