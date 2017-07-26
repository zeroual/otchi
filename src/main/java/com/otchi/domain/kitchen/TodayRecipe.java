package com.otchi.domain.kitchen;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "TODAY_RECIPE")
public class TodayRecipe {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "POST_ID")
    private Long postId;

    @Column(name = "IMAGE")
    private String image;

    @Column(name = "TITLE")
    private String title;


    @Column(name = "DATE")
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "CHEF_ID")
    private Chef chef;


    public TodayRecipe(Long postId, String image, String title, Chef chef) {
        this.postId = postId;
        this.image = image;
        this.title = title;
        this.chef = chef;
    }

    private TodayRecipe() {
    }

    public Long getPostId() {
        return postId;
    }

    public String getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public Chef getChef() {
        return chef;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TodayRecipe that = (TodayRecipe) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(postId, that.postId) &&
                Objects.equals(image, that.image) &&
                Objects.equals(title, that.title) &&
                Objects.equals(date, that.date) &&
                Objects.equals(chef, that.chef);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, postId, image, title, date, chef);
    }
}
