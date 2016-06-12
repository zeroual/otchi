package com.otchi.application.impl;

import com.otchi.application.PublicationsService;
import com.otchi.application.utils.DateFactory;
import com.otchi.domain.kitchen.models.Recipe;
import com.otchi.domain.social.models.Post;
import com.otchi.domain.social.repositories.PostRepository;
import com.otchi.domain.users.models.User;
import com.otchi.domain.users.models.UserRepository;
import com.otchi.infrastructure.storage.BlobStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;


@Service
public class PublicationsServiceImpl implements PublicationsService {

    private PostRepository postRepository;
    private UserRepository userRepository;
    private DateFactory dateFactory;
    private final BlobStorageService blobStorageService;

    @Autowired
    public PublicationsServiceImpl(PostRepository postRepository,
                                   UserRepository userRepository,
                                   DateFactory dateFactory,
                                   BlobStorageService blobStorageService) {

        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.dateFactory = dateFactory;
        this.blobStorageService = blobStorageService;
    }

    @Override
    public Post publishRecipe(Recipe recipe, List<MultipartFile> pictures, String username) {
        Optional<User> user = userRepository.findOneByUsername(username);
        Post post = new Post(dateFactory.now());
        post.withRecipe(recipe);
        post.setAuthor(user.get());
        List<String> imagesURL = blobStorageService.save(pictures);
        recipe.setImages(imagesURL);
        return postRepository.save(post);
    }

}
