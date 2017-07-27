package com.otchi.application;

import api.stepsDefinition.MocakableClock;
import com.otchi.domain.kitchen.Recipe;
import com.otchi.domain.kitchen.TodayRecipe;
import com.otchi.domain.kitchen.TodayRecipeRepository;
import com.otchi.domain.social.models.Post;
import com.otchi.domain.social.repositories.PostRepository;
import com.otchi.domain.users.models.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static com.otchi.domain.users.models.UserBuilder.asUser;
import static java.util.Arrays.asList;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.ReflectionTestUtils.setField;

public class KitchenServiceTest {

    private KitchenService kitchenService;

    @Mock
    private PostRepository postRepository;

    @Mock
    private TodayRecipeRepository todayRecipeRepository;

    private MocakableClock clock = new MocakableClock();
    private Post recipePost;

    @Before

    public void initialise() {
        MockitoAnnotations.initMocks(this);
        kitchenService = new KitchenService(postRepository, todayRecipeRepository, clock);


        recipePost = new Post();
        Recipe recipe = new Recipe("recipe-title", "recipe-description", 1, 3);
        recipePost.setPostContent(recipe);
        setField(recipePost, "id", 73L);
        recipePost.images().addAll(asList("recipe-image1", "recipe-image-2"));
        User chef = asUser().withFirstName("chef-firstName").build();
        recipePost.setAuthor(chef);
    }

    @Test
    public void shouldFetchInDatabaseTheRecipeTheMostLiked() {

        when(postRepository.findFirstRecipePostMostLikedAfter(any())).thenReturn(of(recipePost));
        TodayRecipe todayRecipe = kitchenService.getTodayRecipe().get();
        assertThat(todayRecipe.getPostId()).isEqualTo(73L);
        assertThat(todayRecipe.getTitle()).isEqualTo("recipe-title");
        assertThat(todayRecipe.getImage()).isEqualTo("recipe-image1");
        assertThat(todayRecipe.getChef().getFirstName()).isEqualTo("chef-firstName");

    }

    @Test
    public void shouldUpdateTheTodayRecipeAtMidNightEveryDay() {
        LocalDateTime today = clock.setNowTimeTo("2017-07-24 00:00:00");
        LocalDateTime yesterday = today.minusDays(1);

        when(postRepository.findFirstRecipePostMostLikedAfter(yesterday)).thenReturn(of(recipePost));
        kitchenService.updateTodayRecipeAtMidNight();
        verify(todayRecipeRepository).save(new TodayRecipe(73L, "recipe-image1", "recipe-image1",today.toLocalDate(), any()));

    }

    @Test
    public void shouldNotUpdateTheTodayRecipeAtMidNightEveryDayIfNoRecipeFound() {
        when(postRepository.findFirstRecipePostMostLikedAfter(any())).thenReturn(empty());
        kitchenService.updateTodayRecipeAtMidNight();
        verifyNoMoreInteractions(todayRecipeRepository);

    }

}