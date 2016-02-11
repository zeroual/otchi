package com.otchi.api.facades.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.otchi.api.facades.serializers.CustomDateSerializer;
import com.otchi.domaine.social.models.Comment;

import java.util.Date;

public class CommentDTO implements DTO<Comment> {
    private Long id;
    private AuthorDTO author;
    private String content;

    @JsonSerialize(using = CustomDateSerializer.class)
    private Date createdOn;

    public CommentDTO(Comment comment) {
        extractFromDomain(comment);
    }

    @Override
    public Comment toDomain() {
        throw new UnsupportedOperationException("You try to call  Not Implemented Method, :(");
    }

    @Override
    public void extractFromDomain(Comment comment) {
        this.id = comment.getId();
        this.author = new AuthorDTO(comment.getAuthor());
        this.content = comment.getContent();
        this.createdOn = comment.getCreatedOn();
    }

    public Long getId() {
        return id;
    }

    public AuthorDTO getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public Date getCreatedOn() {
        return createdOn;
    }
}
