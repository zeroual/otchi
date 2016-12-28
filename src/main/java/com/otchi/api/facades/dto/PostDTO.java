package com.otchi.api.facades.dto;

import com.otchi.domain.kitchen.Recipe;
import com.otchi.domain.social.models.Post;
import com.otchi.domain.social.models.Story;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class PostDTO implements DTO<Post> {
    private Long id;
    private AuthorDTO author;

    private LocalDateTime createdTime;

    private AbstractPostContent content;

    private List<UserDTO> likes = new ArrayList<>();
    private List<CommentDTO> comments = new ArrayList<>();

    private boolean liked = false;

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

    @Override
    public Post toDomain() {
        throw new RuntimeException("this function is not implemented yet");
    }

    @Override
    public void extractFromDomain(Post post) {
        this.id = post.getId();
        this.author = new AuthorDTO(post.getAuthor());
        this.createdTime = post.getCreatedTime();
        if (post.getPostContent() instanceof Recipe) {
            this.content = new RecipeDTO((Recipe) post.getPostContent());
        } else if (post.getPostContent() instanceof Story) {
            this.content = new StoryDTO((Story) post.getPostContent());
        }
        this.likes = post.getLikes().stream().map(UserDTO::new).collect(toList());
        this.comments = post.getComments().stream().map(CommentDTO::new).collect(toList());
    }

    public boolean isLiked() {
        return liked;
    }
}
