package com.otchi.api.facades.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.otchi.api.facades.serializers.CustomDateSerializer;
import com.otchi.domaine.social.models.Post;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class PostDTO implements DTO<Post> {
    private Long id;
    private AuthorDTO author;

    @JsonSerialize(using = CustomDateSerializer.class)
    private Date createdTime;

    private AbstractPostContent content;

    private List<UserDTO> likes = new ArrayList<>();

    private PostDTO() {

    }

    public PostDTO(Post post) {
        extractFromDomain(post);
    }

    public Long getId() {
        return id;
    }

    public List<UserDTO> getLikes() {
        return likes;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public AbstractPostContent getContent() {
        return content;
    }

    public AuthorDTO getAuthor() {
        return author;
    }


    @Override
    public Post toDomain() {
        throw new RuntimeException("this function is not implemented yet");
    }

    @Override
    public void extractFromDomain(Post post) {
        this.id = post.getId();
        this.author = new AuthorDTO(post.getAuthor());
        this.createdTime = post.getCreatedTime();
        this.content = new RecipeDTO(post.getRecipe());
        this.likes = post.getLikes().stream().map(UserDTO::new).collect(toList());
    }
}
