package com.otchi.application.impl;

import com.otchi.application.PublicationsService;
import com.otchi.application.utils.Clock;
import com.otchi.domain.events.DomainEvents;
import com.otchi.domain.events.RecipePostedEvent;
import com.otchi.domain.kitchen.Recipe;
import com.otchi.domain.social.models.Post;
import com.otchi.domain.social.models.PostContent;
import com.otchi.domain.social.models.Story;
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
    private Clock clock;
    private final BlobStorageService blobStorageService;
    private final DomainEvents domainEvents;

    // FIXME PublicationsService should not have access to UserRepository directly :(
    @Autowired
    public PublicationsServiceImpl(PostRepository postRepository,
                                   UserRepository userRepository,
                                   Clock clock,
                                   DomainEvents domainEvents,
                                   BlobStorageService blobStorageService) {

        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.clock = clock;
        this.blobStorageService = blobStorageService;
        this.domainEvents = domainEvents;
    }

    @Override
    public Post publishRecipe(Recipe recipe, List<MultipartFile> images, String authorUsername) {
        Post post = publishPost(recipe, images, authorUsername);

        RecipePostedEvent recipePostedEvent = new RecipePostedEvent(recipe);
        domainEvents.raise(recipePostedEvent);
        return post;
    }

    @Override
    public Post publishStory(Story story, List<MultipartFile> images, String authorUsername) {
        return publishPost(story, images, authorUsername);
    }

    //FIXME (abdellah) i don't like that a service change parameter's state (services are stateless)
    private Post publishPost(PostContent postContent, List<MultipartFile> images, String authorUsername) {
        User author = getAuthor(authorUsername);
        List<String> imagesURL = saveImages(images);
        Post post = createPost(author, postContent, imagesURL);
        return postRepository.save(post);
    }

    private Post createPost(User author, PostContent content, List<String> imagesURL) {
        Post post = new Post(clock.now());
        post.setAuthor(author);
        post.setPostContent(content);
        post.attachImages(imagesURL);
        return post;
    }

    private List<String> saveImages(List<MultipartFile> images) {
        List<String> imagesURL = blobStorageService.save(images);
        return imagesURL;
    }

    private User getAuthor(String authorUsername) {
        Optional<User> user = userRepository.findOneByUsername(authorUsername);
        return user.get();
    }

}
