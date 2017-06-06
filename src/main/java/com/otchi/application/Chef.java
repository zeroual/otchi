package com.otchi.application;

public class Chef {

    private Long id;
    private String firstName;
    private String lastName;
    private String picture;

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

    public Chef(Long id, String firstName, String lastName, String picture) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.picture = picture;
    }
}
