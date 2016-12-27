package com.otchi.application.impl;

import com.otchi.application.PublicationsService;
import com.otchi.application.utils.Clock;
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

    @Autowired
    public PublicationsServiceImpl(PostRepository postRepository,
                                   UserRepository userRepository,
                                   Clock clock,
                                   BlobStorageService blobStorageService) {

        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.clock = clock;
        this.blobStorageService = blobStorageService;
    }

    @Override
    public Post publishRecipe(Recipe recipe, List<MultipartFile> images, String authorUsername) {
        return publishPost(recipe, images, authorUsername);
    }

    @Override
    public Post publishStory(Story story, List<MultipartFile> images, String authorUsername) {
        return publishPost(story, images, authorUsername);
    }

    //FIXME (abdellah) i don't like that a service change parameter's state (services are stateless)
    private Post publishPost(PostContent postContent, List<MultipartFile> images, String authorUsername) {
        User author = getAuthor(authorUsername);
        Post post = createPost(author, postContent);
        List<String> imagesURL = saveImages(images);
        postContent.setImages(imagesURL);
        return postRepository.save(post);
    }

    private Post createPost(User author, PostContent content) {
        Post post = new Post(clock.now());
        post.setAuthor(author);
        post.setPostContent(content);
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
