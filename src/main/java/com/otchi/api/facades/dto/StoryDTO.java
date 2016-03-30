package com.otchi.api.facades.dto;

import com.otchi.domain.social.models.Story;

public class StoryDTO extends AbstractPostContent implements DTO<Story> {
    private String content;

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

    public void setContent(String content) {
        this.content = content;
    }

    public Story toDomain() {
        return new Story(content);
    }

    @Override
    public void extractFromDomain(Story story) {
        this.content = story.content();
    }
}
