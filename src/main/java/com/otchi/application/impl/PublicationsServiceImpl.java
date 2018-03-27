package com.otchi.application.impl;

import com.otchi.application.Feed;
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
    public Feed publishRecipe(Recipe recipe, List<MultipartFile> images, String authorUsername) {
        return publishPost(recipe, images, authorUsername);
    }

    @Override
    public Feed publishStory(Story story, List<MultipartFile> images, String authorUsername) {
        return publishPost(story, images, authorUsername);
    }

    private Feed publishPost(PostContent postContent, List<MultipartFile> images, String authorUsername) {
        User author = getAuthor(authorUsername);
        List<String> imagesURL = saveImages(images);
        Post post = createPost(author, postContent, imagesURL);
        postRepository.save(post);
        return new Feed(post, authorUsername);
    }

    private Post createPost(User author, PostContent content, List<String> imagesURL) {
        Post post = new Post(clock.now());
        post.setAuthor(author);
        post.setPostContent(content);
        post.attachImages(imagesURL);
        return post;
    }

    private List<String> saveImages(List<MultipartFile> images) {
        return blobStorageService.save(images);
    }

    private User getAuthor(String authorUsername) {
        Optional<User> user = userRepository.findOneByUsername(authorUsername);
        return user.get();
    }

}
