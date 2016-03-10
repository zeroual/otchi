package com.otchi.api;


import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.otchi.domaine.users.models.User;
import com.otchi.domaine.users.models.UserRepository;
import com.otchi.infrastructure.config.ResourcesPath;
import com.otchi.utils.AbstractControllerTest;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class FollowUserResourceTest extends AbstractControllerTest {


    @Autowired
    private UserRepository userRepository;

    @Test
    @DatabaseSetup("/dbunit/users/users.xml")
    @Transactional
    public void ShouldfollowUser() throws Exception {
        mockMvc.perform(post(ResourcesPath.ME + "/following/2/")
                .with(user("zeroual.abde@gmail.com"))
                .with(csrf()).contentType(contentType))
                .andExpect(status().isOk());

        User user = userRepository.findOne(2L);
        Assertions.assertThat(user.getFollowing()).hasSize(1);
    }

    @Test
    @DatabaseSetup("/dbunit/users/users.xml")
    @Transactional
    public void ShouldGetAllPossibleFollowers() throws Exception {
        mockMvc.perform(get(ResourcesPath.ME + "/following/")
                .with(user("zeroual.abde@gmail.com"))
                .with(csrf()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

}
