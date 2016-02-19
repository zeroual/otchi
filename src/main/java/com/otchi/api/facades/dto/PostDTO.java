package com.otchi.api.facades.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.otchi.api.facades.serializers.CustomDateSerializer;
import com.otchi.domaine.social.models.Post;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PostDTO implements DTO<Post> {
    private String id;
    private AuthorDTO author;

    @JsonSerialize(using = CustomDateSerializer.class)
    private Date postingDate;

    private AbstractPostContent content;

    private List<String> likers = new ArrayList<>();

    private PostDTO() {

    }

    public PostDTO(Post post) {
        extractFromDomain(post);
    }

    public String getId() {
        return id;
    }
    public int getLikes(){ return likers.size(); }

    public Date getPostingDate() {
        return postingDate;
    }

    public AbstractPostContent getContent() {
        return content;
    }

    public AuthorDTO getAuthor() {
        return author;
    }

    public List<String> getLikers() {
        return likers;
    }

    @Override
    public Post toDomain() {
        throw new RuntimeException("this function is not implemented yet");
    }

    @Override
    public void extractFromDomain(Post post) {
        this.id = post.getId();
        this.author = new AuthorDTO(post.getAuthor());
        this.postingDate = post.getCreationDate();
        this.content = new RecipeDTO(post.getRecipe());
        this.likers=new ArrayList<>(post.getLikers());
    }
}
