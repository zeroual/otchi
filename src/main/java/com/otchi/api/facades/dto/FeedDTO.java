package com.otchi.api.facades.dto;

import com.otchi.application.Feed;
import com.otchi.domain.kitchen.Recipe;
import com.otchi.domain.social.models.Story;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class FeedDTO {
    private Long id;
    private AuthorDTO author;
    private LocalDateTime createdTime;
    private AbstractPostContent content;
    private List<UserDTO> likes = new ArrayList<>();
    private List<CommentDTO> comments = new ArrayList<>();
    private boolean liked = false;
    private boolean canBeRemoved = false;
    private List<String> images;
    private Integer likesCount;


    private FeedDTO() {

    }

    public FeedDTO(Feed feed) {
        this.id = feed.getId();
        this.author = new AuthorDTO(feed.getAuthor());
        this.createdTime = feed.getCreatedTime();
        if (feed.getContent() instanceof Recipe) {
            this.content = new RecipeDTO((Recipe) feed.getContent());
        } else if (feed.getContent() instanceof Story) {
            this.content = new StoryDTO((Story) feed.getContent());
        }
        this.likes = feed.getLikes().stream().map(UserDTO::new).collect(toList());
        this.likesCount = feed.getLikesCount();
        this.comments = feed.getComments().stream().map(CommentDTO::new).collect(toList());
        this.canBeRemoved = feed.canBeRemoved();
        this.images = feed.images();
        this.liked = feed.isLiked();

    }

    public Long getId() {
        return id;
    }

    public List<UserDTO> getLikes() {
        return likes;
    }

    public LocalDateTime getCreatedTime() {
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

    public List<String> getImages() {
        return images;
    }

    public Integer getLikesCount() {
        return likesCount;
    }
}
