package com.otchi.domain.kitchen;

import com.otchi.domain.users.models.User;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Table(name = "USERS")
public class Chef {

    @Id
    @Column(name = "ID")
    private Long id;

    @NotNull
    @Column(name = "FIRST_NAME", length = 50)
    private String firstName;

    @NotNull
    @Column(name = "LAST_NAME", length = 50)
    private String lastName;

    @Column(name = "PICTURE")
    private String picture;


    private Chef() {
    }

    public Chef(User chef) {
        this.id = chef.getId();
        this.firstName = chef.getFirstName();
        this.lastName = chef.getLastName();
        this.picture = chef.picture();
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

    public Chef(Long id, String firstName, String lastName, String picture) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.picture = picture;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chef chef = (Chef) o;
        return Objects.equals(id, chef.id) &&
                Objects.equals(firstName, chef.firstName) &&
                Objects.equals(lastName, chef.lastName) &&
                Objects.equals(picture, chef.picture);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, picture);
    }
}
