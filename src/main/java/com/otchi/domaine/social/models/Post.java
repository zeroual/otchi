package com.otchi.domaine.social.models;

import com.otchi.domaine.kitchen.models.Recipe;
import com.otchi.domaine.users.models.User;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "POST")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "CREATED_AT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "RECIPE_ID")
    private Recipe recipe;

    @ElementCollection
    @CollectionTable(
            name = "POST_LIKES",
            joinColumns=@JoinColumn(name = "POST_ID")
    )
    @Column(name="USER_ID")
    private Set<Long> likers = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "AUTHOR_ID")
    private User author;

    public Post(Date creationDate) {
        this.creationDate=creationDate;
    }

    public Post() {

    }

    public void addLike(Long userId){
        this.likers.add(userId);
    }

    public void unLike (Long userId){ this.likers.remove(userId);}

    public Date getCreationDate() {
        return creationDate;
    }

    public Set<Long> getLikers() {
        return likers;
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

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }
}
