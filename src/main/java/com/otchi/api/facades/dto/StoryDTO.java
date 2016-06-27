package com.otchi.api.facades.dto;

import com.otchi.domain.social.models.Story;

import java.util.ArrayList;
import java.util.List;

public class StoryDTO extends AbstractPostContent implements DTO<Story> {
    private String content;

    private List<String> images = new ArrayList<>();

    private StoryDTO() {
        super("STORY");
    }

    public StoryDTO(String content) {
        this();
        this.content = content;
    }

    public StoryDTO(Story story) {
        this();
        extractFromDomain(story);
    }

    public String getContent() {
        return content;
    }

    public List<String> getImages() {
        return images;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Story toDomain() {
        return new Story(content);
    }

    @Override
    public void extractFromDomain(Story story) {
        this.content = story.content();
        this.images = story.getImages();
    }
}
