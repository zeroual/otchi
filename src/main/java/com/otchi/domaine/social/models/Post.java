package com.otchi.domaine.social.models;

import com.otchi.domaine.kitchen.models.Recipe;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "POST")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    //FIXME use LocalDate
    @Column(name = "CREATED_AT")
    @Temporal(TemporalType.DATE)
    private Date creationDate;

    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "RECIPE_ID")

    private Recipe recipe;

    public Post(Date creationDate) {
        this.creationDate=creationDate;
    }

    public Post() {

    }

    public Date getCreationDate() {
        return creationDate;
    }


    public Recipe getRecipe() {
        return recipe;
    }

    public Post withRecipe(Recipe recipe) {
        this.recipe = recipe;
        return this;
    }

    public Long getId() {
        return id;
    }

}
