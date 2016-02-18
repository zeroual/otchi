package com.otchi.api;


import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.otchi.domaine.social.models.Post;
import com.otchi.domaine.social.repositories.PostRepository;
import com.otchi.infrastructure.config.ResourcesPath;
import com.otchi.utils.AbstractControllerTest;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class FeedResourceTest extends AbstractControllerTest {

    @Autowired
    private PostRepository postRepository;

    //FIXME : i guess if we write integration test with cucumber will be better
    @Test
    @DatabaseSetup("/dbunit/social/stream-feeds.xml")
    @Transactional
    public void shouldFetchAllRecipes() throws Exception {
        mockMvc.perform(get(ResourcesPath.FEED).with(user("user"))
                .contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":1,\"author\":{\"id\":1,\"firstName\":\"Abdellah\",\"lastName\":\"ZEROUAL\"},\"postingDate\":\"2015-02-28 00:00:00\",\"content\":{\"type\":\"RECIPE\",\"id\":2,\"description\":null,\"cookTime\":null,\"preparationTime\":null,\"ingredients\":[],\"instructions\":[],\"title\":\"TITLE_SAMPLE_2\"}}]"));
    }

    @Test
    @DatabaseSetup("/dbunit/social/stream-feeds.xml")
    @Transactional
    public void shouldLikeAPost() throws Exception {
        mockMvc.perform(post(ResourcesPath.FEED + "/1/like")
                .with(user("user"))
                .with(csrf()).contentType(contentType))
                .andExpect(status().isOk());
        Post post = postRepository.findOne(1L);
        Assertions.assertThat(post.getLikers()).hasSize(1);
    }
}