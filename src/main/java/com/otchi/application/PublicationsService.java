package com.otchi.application;

import com.otchi.domaine.social.models.Post;

public interface PublicationsService {
    Post createNewPost(Post post);
}
