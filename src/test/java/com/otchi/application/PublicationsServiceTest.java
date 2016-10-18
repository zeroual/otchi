package com.otchi.application;

import com.otchi.application.impl.PublicationsServiceImpl;
import com.otchi.application.utils.DateFactory;
import com.otchi.domain.kitchen.Recipe;
import com.otchi.domain.social.models.Post;
import com.otchi.domain.social.models.Story;
import com.otchi.domain.social.repositories.PostRepository;
import com.otchi.domain.social.repositories.mocks.MockPostRepository;
import com.otchi.domain.users.models.User;
import com.otchi.domain.users.models.UserRepository;
import com.otchi.domain.users.models.mocks.MockUserRepository;
import com.otchi.infrastructure.storage.BlobStorageService;
import com.otchi.utils.mocks.MockCrudRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class PublicationsServiceTest {

    private PublicationsService publicationsService;
    private PostRepository postRepository;
    private UserRepository userRepository;
    private DateFactory dateFactory = mock(DateFactory.class);
    private BlobStorageService blobStorageService = mock(BlobStorageService.class);
    private List<MultipartFile> NO_IMAGES = emptyList();

    @Before
    public void setUp() {
        MockCrudRepository.clearDatabase();
        postRepository = new MockPostRepository();
        userRepository = new MockUserRepository();

        userRepository.save(new User("email@gmail.com", "abde", "zeros"));
        publicationsService = new PublicationsServiceImpl(postRepository, userRepository, dateFactory, blobStorageService);

    }

    @Test
    public void shouldCreateNewPostWithRecipeAsContent() {
        Recipe recipe = new Recipe("recipe_title", "recipe_desc", 50, 20, new HashSet<String>(Arrays.asList("moroccan", "salt")));
        publicationsService.publishRecipe(recipe, emptyList(), "email@gmail.com");
        Post savedPost = postRepository.findOne(1L);
        assertThat(savedPost).isNotNull();
        assertThat(savedPost.getPostContent()).isNotNull();
        assertThat(((Recipe) savedPost.getPostContent()).getTitle()).isEqualTo("recipe_title");
    }

    @Test
    public void shouldAssignRecipeToHisAuthor() {
        Recipe recipe = new Recipe("recipe_title", "recipe_desc", 50, 20);
        publicationsService.publishRecipe(recipe, emptyList(), "email@gmail.com");
        Post savedPost = postRepository.findOne(1L);
        assertThat(savedPost).isNotNull();
        assertThat(savedPost.getAuthor()).isNotNull();
        assertThat(savedPost.getAuthor().getFirstName()).isEqualTo("abde");

    }

    @Test
    public void shouldAssignPostCreationDateToNow() throws ParseException {
        Date now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").parse("2015-02-28 12:15:22.8");
        Mockito.when(dateFactory.now()).thenReturn(now);

        Recipe recipe = new Recipe("recipe_title", "recipe_desc", 50, 20);
        publicationsService.publishRecipe(recipe, emptyList(), "email@gmail.com");
        Post savedPost = postRepository.findOne(1L);
        assertThat(savedPost.getCreatedTime()).isEqualTo(now);
    }

    @Test
    public void shouldSaveRecipePictures() {

        List<String> imagesURL = asList("http://url.com/12OU222Y1Y2.png");
        when(blobStorageService.save(anyListOf(MultipartFile.class))).thenReturn(imagesURL);

        MultipartFile picture1 = new MockMultipartFile("food1.png", "some data".getBytes());
        List<MultipartFile> pictures = asList(picture1);

        Recipe recipe = new Recipe("recipe_title", "recipe_desc", 50, 20);
        Post savedPost = publicationsService.publishRecipe(recipe, pictures, "email@gmail.com");
        Mockito.verify(blobStorageService).save(pictures);
        Recipe savedRecipe = (Recipe) savedPost.getPostContent();
        assertThat(savedRecipe.getImages()).hasSize(1)
                .isEqualTo(imagesURL);
    }

    @Test
    public void shouldSaveStoryAsPost() {
        Story story = new Story("my story");
        Post savedPost = publicationsService.publishStory(story, NO_IMAGES, "email@gmail.com");
        assertThat(savedPost.getId()).isNotNull();
        savedPost = postRepository.findOne(1L);
        assertThat(savedPost).isNotNull();

        Story savedStory = (Story) savedPost.getPostContent();
        assertThat(savedStory.content()).isEqualTo("my story");
    }

    @Test
    public void shouldAssignStoryPostToHisAuthor() {
        Story story = new Story("my story");
        String username = "email@gmail.com";
        Post savedPost = publicationsService.publishStory(story, NO_IMAGES, username);
        assertThat(savedPost.getAuthor().getUsername()).isEqualTo(username);

    }

    @Test
    public void shouldAssignStoryCreationDateToNow() throws ParseException {
        Date now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").parse("2015-02-28 12:15:22.8");
        Mockito.when(dateFactory.now()).thenReturn(now);

        Story story = new Story("my story");

        publicationsService.publishStory(story, NO_IMAGES, "email@gmail.com");
        Post savedPost = postRepository.findOne(1L);
        assertThat(savedPost.getCreatedTime()).isEqualTo(now);
    }

    @Test
    public void shouldSaveStoryImages() {

        List<String> imagesURL = asList("http://url.com/12OU222Y1Y2.png");
        when(blobStorageService.save(anyListOf(MultipartFile.class))).thenReturn(imagesURL);

        MultipartFile image = new MockMultipartFile("food1.png", "some data".getBytes());
        List<MultipartFile> images = asList(image);

        Story story = new Story("my story");
        Post savedPost = publicationsService.publishStory(story, images, "email@gmail.com");
        Mockito.verify(blobStorageService).save(images);
        Story savedStory = (Story) savedPost.getPostContent();
        assertThat(savedStory.getImages()).hasSize(1)
                .isEqualTo(imagesURL);


    }
}
