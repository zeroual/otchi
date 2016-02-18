package com.otchi.domaine.social.models;

import com.otchi.domaine.Helper.SequenceDao;
import com.otchi.domaine.kitchen.models.Recipe;
import com.otchi.domaine.users.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Document(collection = "posts")
public class Post {

    @Autowired
    private SequenceDao sequenceDao;

    public void setId() {
        this.id = 1L;//sequenceDao.getNextSequenceId("posting");
    }

    @Id
    private Long id;

    private Date creationDate;

    @DBRef
    private Recipe recipe;

    private Set<Long> likers = new HashSet<>();

    @DBRef
    private User author;

    public Post(Date creationDate) {
        this.creationDate=creationDate;
    }

    public Post() {

    }

    public void addLike(Long userId){
        this.likers.add(userId);
    }

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
