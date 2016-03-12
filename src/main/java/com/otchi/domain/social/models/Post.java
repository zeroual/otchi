package com.otchi.domain.social.models;

import com.otchi.domain.kitchen.models.Recipe;
import com.otchi.domain.users.models.User;

import javax.persistence.*;
import java.util.*;


@Entity
@Table(name = "POST")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "CREATED_AT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdTime;

    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "RECIPE_ID")
    private Recipe recipe;

    @ManyToOne
    @JoinColumn(name = "AUTHOR_ID")
    private User author;

    public Post(Date createdTime) {
        this.createdTime = createdTime;
    }

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "POST_LIKES",
            joinColumns = {@JoinColumn(name = "POST_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "USER_ID", referencedColumnName = "ID")}
    )
    private Set<User> likes = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "POST_ID")
    private Collection<Comment> comments = new ArrayList<>();



    public Post() {

    }

    public void addLike(User user) {
        this.likes.add(user);
    }
    public void addComment(Comment comment) {
        this.comments.add(comment);
    }

    public void unLike(User user) {
        this.likes.remove(user);
    }

    public Date getCreatedTime() {
        return createdTime;
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

    public Set<User> getLikes() {
        return likes;
    }

    public Collection<Comment> getComments() {
        return comments;
    }

}
