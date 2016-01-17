package com.otchi.api.facades;

import com.otchi.domaine.social.models.Post;

public class PostDTO {
    private Long id;

    public PostDTO() {

    }

    public PostDTO(Post post) {

    }

    public Long getId() {
        return id;
    }

}
