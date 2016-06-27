package com.otchi.domain.social.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "STORY")
public class Story extends PostContent {

    @Column(name = "CONTENT")
    private String content;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "STORY_IMAGES", joinColumns = @JoinColumn(name = "STORY_ID", referencedColumnName = "ID"))
    @Column(name = "URL")
    private List<String> images;

    private Story() {

    }

    public Story(String content) {

        this.content = content;
    }

    public String content() {
        return content;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
