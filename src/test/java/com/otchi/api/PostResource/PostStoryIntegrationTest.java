package com.otchi.api.postResource;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.otchi.api.facades.dto.StoryDTO;
import com.otchi.domain.social.models.Post;
import com.otchi.domain.social.models.Story;
import com.otchi.domain.social.repositories.PostRepository;
import com.otchi.infrastructure.config.ResourcesPath;
import com.otchi.utils.AbstractIntegrationTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PostStoryIntegrationTest extends AbstractIntegrationTest {


    @Autowired
    private PostRepository postRepository;


    @Test
    @DatabaseSetup("/dbunit/users/users.xml")
    public void shouldCreateNewStoryAsPost() throws Exception {
        String contentToShare = "i am so happy that i found this restaurant";
        StoryDTO newStory = new StoryDTO(contentToShare);

        mockMvc.perform(post(ResourcesPath.POST + "/story")
                .content(json(newStory))
                .with(user("mr.jaifar@gmail.com"))
                .with(csrf()).contentType(contentType))
                .andExpect(status().isCreated());

        Post savedPost = postRepository.findOne(1L);
        Story story = (Story) savedPost.getPostContent();
        assertThat(story).isNotNull();
        assertThat(story.content()).isEqualTo(contentToShare);
    }

    @Test
    @DatabaseSetup("/dbunit/users/users.xml")
    public void shouldReturnNewStoryPostAsResponseBody() throws Exception {
        String contentToShare = "i am so happy that i found this restaurant";
        StoryDTO newStory = new StoryDTO(contentToShare);

        mockMvc.perform(post(ResourcesPath.POST + "/story")
                .content(json(newStory))
                .with(user("mr.jaifar@gmail.com"))
                .with(csrf()).contentType(contentType))
                .andExpect(status().isCreated())
                .andExpect(content().json("{\"id\":1,\"author\":{\"id\":2,\"firstName\":\"Reda\",\"lastName\":\"JAIFAR\"},\"content\":{\"type\":\"STORY\",\"content\":\"i am so happy that i found this restaurant\"},\"likes\":[],\"comments\":[]}"));
    }


    @Test
    @DatabaseSetup("/dbunit/users/users.xml")
    public void shouldAssignNewStoryToAuhtenticatedUser() throws Exception {
        StoryDTO newStory = new StoryDTO("i am so happy that i found this restaurant");

        String authenticatedUsername = "mr.jaifar@gmail.com";
        mockMvc.perform(post(ResourcesPath.POST + "/story")
                .content(json(newStory))
                .with(user(authenticatedUsername))
                .with(csrf()).contentType(contentType));

        Post savedPost = postRepository.findOne(1L);
        assertThat(savedPost.getAuthor().getUsername()).isEqualTo(authenticatedUsername);

    }
}
