package com.otchi.api.facades.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.otchi.api.facades.serializers.CustomDateSerializer;
import com.otchi.application.Feed;
import com.otchi.domain.kitchen.Recipe;
import com.otchi.domain.social.models.Story;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class FeedDTO {
    private Long id;
    private AuthorDTO author;

    @JsonSerialize(using = CustomDateSerializer.class)
    private Date createdTime;

    private AbstractPostContent content;

    private List<UserDTO> likes = new ArrayList<>();
    private List<CommentDTO> comments = new ArrayList<>();

    private boolean liked = false;

    private boolean canBeRemoved = false;

    private FeedDTO() {

    }

    public FeedDTO(Feed feed) {
        this.id = feed.getId();
        this.author = new AuthorDTO(feed.getAuthor());
        this.createdTime = feed.getCreatedTime();
        if (feed.getPostContent() instanceof Recipe) {
            this.content = new RecipeDTO((Recipe) feed.getPostContent());
        } else if (feed.getPostContent() instanceof Story) {
            this.content = new StoryDTO((Story) feed.getPostContent());
        }
        this.likes = feed.getLikes().stream().map(UserDTO::new).collect(toList());
        this.comments = feed.getComments().stream().map(CommentDTO::new).collect(toList());
        this.canBeRemoved = feed.canBeRemoved();

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


    public List<CommentDTO> getComments() {
        return comments;
    }


    public void extractFromDomain(Feed post) {
    }

    public boolean isLiked() {
        return liked;
    }

    public boolean isCanBeRemoved() {
        return this.canBeRemoved;
    }
}
