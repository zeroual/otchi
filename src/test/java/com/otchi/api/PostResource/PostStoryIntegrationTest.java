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
import org.springframework.mock.web.MockMultipartFile;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PostStoryIntegrationTest extends AbstractIntegrationTest {


    @Autowired
    private PostRepository postRepository;

    MockMultipartFile image1 = new MockMultipartFile("images", "food1.jpg", "image/jpeg", new byte[]{});
    MockMultipartFile image2 = new MockMultipartFile("images", "food2.jpg", "image/jpeg", new byte[]{});


    @Test
    @DatabaseSetup("/dbunit/users/users.xml")
    public void shouldCreateNewStoryAsPost() throws Exception {
        String contentToShare = "i am so happy that i found this restaurant";
        StoryDTO newStory = new StoryDTO(contentToShare);

        MockMultipartFile storyJsonFile = new MockMultipartFile("story", "", "application/json", json(newStory).getBytes());
        mockMvc.perform(fileUpload(ResourcesPath.POST + "/story")
                .file(storyJsonFile)
                .file(image1)
                .file(image2)
                .with(user("mr.jaifar@gmail.com"))
                .with(csrf()).contentType(MULTIPART_FORM_DATA))
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

        MockMultipartFile storyJsonFile = new MockMultipartFile("story", "", "application/json", json(newStory).getBytes());
        mockMvc.perform(fileUpload(ResourcesPath.POST + "/story")
                .file(storyJsonFile)
                .file(image1)
                .with(user("mr.jaifar@gmail.com"))
                .with(csrf()).contentType(MULTIPART_FORM_DATA))
                .andExpect(status().isCreated())
                .andExpect(content().json("{\"id\":1,\"author\":{\"id\":2,\"firstName\":\"Reda\",\"lastName\":\"JAIFAR\"},\"content\":{\"type\":\"STORY\",\"content\":\"i am so happy that i found this restaurant\"},\"likes\":[],\"comments\":[]}"));
    }


    @Test
    @DatabaseSetup("/dbunit/users/users.xml")
    public void shouldAssignNewStoryToAuhtenticatedUser() throws Exception {
        String contentToShare = "i am so happy that i found this restaurant";
        StoryDTO newStory = new StoryDTO(contentToShare);
        String authenticatedUsername = "mr.jaifar@gmail.com";

        MockMultipartFile storyJsonFile = new MockMultipartFile("story", "", "application/json", json(newStory).getBytes());
        mockMvc.perform(fileUpload(ResourcesPath.POST + "/story")
                .file(storyJsonFile)
                .with(user("mr.jaifar@gmail.com"))
                .with(csrf()).contentType(MULTIPART_FORM_DATA));

        Post savedPost = postRepository.findOne(1L);
        assertThat(savedPost.getAuthor().getUsername()).isEqualTo(authenticatedUsername);

    }
}
