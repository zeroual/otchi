package com.otchi.application;

import com.otchi.application.utils.Clock;
import com.otchi.domain.kitchen.Chef;
import com.otchi.domain.kitchen.Recipe;
import com.otchi.domain.kitchen.TodayRecipe;
import com.otchi.domain.kitchen.TodayRecipeRepository;
import com.otchi.domain.social.models.Post;
import com.otchi.domain.social.repositories.PostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static java.util.Optional.of;

@Service
public class KitchenService {

    private final PostRepository postRepository;
    private final TodayRecipeRepository todayRecipeRepository;
    private Clock clock;
    private static final Logger log = LoggerFactory.getLogger(KitchenService.class);


    @Autowired
    public KitchenService(PostRepository postRepository, TodayRecipeRepository todayRecipeRepository, Clock clock) {
        this.postRepository = postRepository;
        this.todayRecipeRepository = todayRecipeRepository;
        this.clock = clock;
    }

    public Optional<TodayRecipe> getTodayRecipe() {
        LocalDateTime now = clock.now();
        LocalDateTime yesterday = now.minusDays(1);
        Optional<Post> foundPost = postRepository.findFirstRecipePostMostLikedAfter(yesterday);
        return foundPost.flatMap(
                post -> {
                    Recipe recipe = (Recipe) post.getPostContent();
                    TodayRecipe todayRecipe = new TodayRecipe(post.getId(), post.images().get(0), recipe.getTitle(),
                            now.toLocalDate(), new Chef(post.getAuthor()));
                    return of(todayRecipe);
                }
        );
    }

    @Scheduled(cron = "00 00 * * * *")
    @Transactional
    public void updateTodayRecipeAtMidNight() {
        Optional<TodayRecipe> todayRecipe = getTodayRecipe();
        todayRecipe.ifPresent(todayRecipeRepository::save);
        log.info("Update the today recipe - {}", LocalDate.now());
    }
}
