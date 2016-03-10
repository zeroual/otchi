package com.otchi.api;


import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.otchi.domain.users.models.User;
import com.otchi.domain.users.models.UserRepository;
import com.otchi.infrastructure.config.ResourcesPath;
import com.otchi.utils.AbstractControllerTest;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class FollowingResourceTest extends AbstractControllerTest {


    @Autowired
    private UserRepository userRepository;

    @Test
    @DatabaseSetup("/dbunit/social/following.xml")
    @Transactional
    public void ShouldFollowUser() throws Exception {
        mockMvc.perform(post(ResourcesPath.ME + "/following/")
                .content("2")
                .with(user("zeroual.abde@gmail.com"))
                .with(csrf()).contentType(contentType))
                .andExpect(status().isCreated());

        User user = userRepository.findOne(1L);
        Assertions.assertThat(user.getFollowing()).hasSize(1).extracting(User::getEmail)
                .containsExactly("mr.jaifar@gmail.com");
    }
}
