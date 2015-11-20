package com.otchi.domain.kitchen.models;


import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "RECIPE")
public class Recipe{

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "DESCRIPTION")
    private String description;


    public Recipe() {
    }

    public Recipe(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    //FIXME to remove
    public void setId(Long id) {
        this.id = id;
    }
}
