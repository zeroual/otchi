package com.otchi.api.facades.dto;


import com.otchi.domain.users.models.User;

public class AuthorDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String picture;


    public AuthorDTO(User author) {
        this.id = author.getId();
        this.firstName = author.getFirstName();
        this.lastName = author.getLastName();
        this.picture = author.picture();
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
}
