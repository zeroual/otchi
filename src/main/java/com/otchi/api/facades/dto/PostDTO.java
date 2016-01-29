package com.otchi.api.facades.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.otchi.api.facades.serializers.CustomDateSerializer;
import com.otchi.domaine.social.models.Post;

import java.util.Date;

public class PostDTO implements DTO<Post> {
    private Long id;
    private AuthorDTO author;

    @JsonSerialize(using = CustomDateSerializer.class)
    private Date postingDate;

    private AbstractPostContent content;

    private PostDTO() {

    }

    public PostDTO(Post post) {
        extractFromDomain(post);
    }

    public Long getId() {
        return id;
    }


    public Date getPostingDate() {
        return postingDate;
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
        //TODO change with the real author when the authentication system is available
        this.author = new AuthorDTO(1L, "Abdellah", "ZEROUAL");
        this.postingDate = post.getCreationDate();
        this.content = new RecipeDTO(post.getRecipe());
    }
}