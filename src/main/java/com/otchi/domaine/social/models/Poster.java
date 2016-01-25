package com.otchi.domaine.social.models;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "POSTER")
public class Poster {

    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    public String firstName() {
        return firstName;
    }

    public String lastName() {
        return lastName;
    }
}
