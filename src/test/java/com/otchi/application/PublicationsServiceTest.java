package com.otchi.application;

import com.otchi.application.impl.PublicationsServiceImpl;
import com.otchi.application.utils.DateFactory;
import com.otchi.domaine.kitchen.models.Recipe;
import com.otchi.domaine.social.models.Post;
import com.otchi.domaine.social.repositories.PostRepository;
import com.otchi.domaine.social.repositories.mocks.MockPostRepository;
import com.otchi.domaine.users.models.User;
import com.otchi.domaine.users.models.UserRepository;
import com.otchi.domaine.users.models.mocks.MockUserRepository;
import com.otchi.utils.mocks.MockCrudRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

public class PublicationsServiceTest {

    private PublicationsService publicationsService;
    private PostRepository postRepository;
    private UserRepository userRepository;
    private DateFactory dateFactory = Mockito.mock(DateFactory.class);

    @Before
    public void setUp() {
        MockCrudRepository.clearDatabase();
        postRepository = new MockPostRepository();
        userRepository = new MockUserRepository();

        userRepository.save(new User("abde", "zeros", "email@gmail.com"));
        publicationsService = new PublicationsServiceImpl(postRepository, userRepository, dateFactory);

    }

    @Test
    public void shouldCreateNewPostWithRecipeAsContent() {
        Recipe recipe = new Recipe("recipe_title", "recipe_desc", 50, 20);
        publicationsService.publishRecipe(recipe, "email@gmail.com");
        Post savedPost = postRepository.findOne("1");
        assertThat(savedPost).isNotNull();
        assertThat(savedPost.getRecipe()).isNotNull();
        assertThat(savedPost.getRecipe().getTitle()).isEqualTo("recipe_title");
    }

    @Test
    public void shouldAssignRecipeToHisAuthor() {
        Recipe recipe = new Recipe("recipe_title", "recipe_desc", 50, 20);
        publicationsService.publishRecipe(recipe, "email@gmail.com");
        Post savedPost = postRepository.findOne("1");
        assertThat(savedPost).isNotNull();
        assertThat(savedPost.getAuthor()).isNotNull();
        assertThat(savedPost.getAuthor().getFirstName()).isEqualTo("abde");

    }

    @Test
    public void shouldAssignPostCreationDateToNow() throws ParseException {
        Date now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").parse("2015-02-28 12:15:22.8");
        Mockito.when(dateFactory.now()).thenReturn(now);

        Recipe recipe = new Recipe("recipe_title", "recipe_desc", 50, 20);
        publicationsService.publishRecipe(recipe, "email@gmail.com");
        Post savedPost = postRepository.findOne("1");
        assertThat(savedPost.getCreationDate()).isEqualTo(now);
    }
}