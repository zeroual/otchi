package com.otchi.domaine.social.models;

import com.otchi.domaine.kitchen.models.Recipe;
import com.otchi.domaine.users.models.User;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Document(collection = "posts")
public class Post {

    @Id
    private String id;

    private Date creationDate;

    @DBRef
    private Recipe recipe;

    private Set<String> likers = new HashSet<>();

    @DBRef
    private User author;

    public Post(Date creationDate) {
        this.creationDate=creationDate;
    }

    public Post() {

    }

    public void addLike(String userId){
        this.likers.add(userId);
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Set<String> getLikers() {
        return likers;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public Post withRecipe(Recipe recipe) {
        this.recipe = recipe;
        return this;
    }

    public String getId() {
        return id;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }
}
