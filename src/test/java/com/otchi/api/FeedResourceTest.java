package com.otchi.api;


import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.otchi.domain.social.models.Post;
import com.otchi.domain.social.repositories.PostRepository;
import com.otchi.domain.users.models.User;
import com.otchi.infrastructure.config.ResourcesPath;
import com.otchi.utils.AbstractIntegrationTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class FeedResourceTest extends AbstractIntegrationTest {

    @Autowired
    private PostRepository postRepository;

    /*
        TODO : refactor those integration tests to be more readable :)
     i recommended that someone else take the next test and translate it to cucumber format.
     i will be only a shadow if he or she needs help :joy
     */

    @Test
    @DatabaseSetup("/dbunit/social/stream-feeds.xml")
    @Transactional
    public void shouldLikeAPost() throws Exception {
        mockMvc.perform(post(ResourcesPath.FEED + "/1/like")
                .with(user("zeroual.abde@gmail.com"))
                .with(csrf()).contentType(contentType))
                .andExpect(status().isOk());
        Post post = postRepository.findOne(1L);
        assertThat(post.getLikes()).hasSize(1);
        assertThat(post.getLikes())
                .extracting(User::getFirstName)
                .contains("Abdellah");
    }

    @Test
    @DatabaseSetup("/dbunit/social/liked-feed.xml")
    @Transactional
    public void shouldUnlikeAPost() throws Exception {
        mockMvc.perform(post(ResourcesPath.FEED + "/1/unlike")
                .with(user("zeroual.abde@gmail.com"))
                .with(csrf()).contentType(contentType))
                .andExpect(status().isOk());
        Post post = postRepository.findOne(1L);
        assertThat(post.getLikes()).hasSize(0);
    }

}
