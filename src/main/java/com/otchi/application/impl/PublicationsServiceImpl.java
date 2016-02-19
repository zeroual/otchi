package com.otchi.application.impl;

import com.otchi.application.PublicationsService;
import com.otchi.application.utils.DateFactory;
import com.otchi.domaine.kitchen.models.Recipe;
import com.otchi.domaine.social.models.Post;
import com.otchi.domaine.social.repositories.PostRepository;
import com.otchi.domaine.users.models.User;
import com.otchi.domaine.users.models.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class PublicationsServiceImpl implements PublicationsService {

    private PostRepository postRepository;
    private UserRepository userRepository;
    private DateFactory dateFactory;

    @Autowired
    public PublicationsServiceImpl(PostRepository postRepository, UserRepository userRepository, DateFactory dateFactory) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.dateFactory = dateFactory;
    }

    @Override
    public Post publishRecipe(Recipe recipe, String username) {
        Optional<User> user = userRepository.findOneByEmail(username);
        Post post = new Post(dateFactory.now());
        post.withRecipe(recipe);
        post.setAuthor(user.get());
        return postRepository.save(post);
    }

}
