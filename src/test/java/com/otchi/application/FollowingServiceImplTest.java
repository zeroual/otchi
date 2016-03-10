package com.otchi.application;

import com.otchi.application.impl.FollowingServiceImpl;
import com.otchi.domain.users.exceptions.UserNotFoundException;
import com.otchi.domain.users.models.User;
import com.otchi.domain.users.models.UserRepository;
import com.otchi.domain.users.models.mocks.MockUserRepository;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FollowingServiceImplTest {

    private UserRepository userRepository = new MockUserRepository();
    private FollowingService followingService = new FollowingServiceImpl(userRepository);


    @Before
    public void setUp() {

        User user = new User("toto@foo.com");
        user.setFirstName("firstName_test");
        userRepository.save(user);

        User userToFollow = new User("totobis@foo.com");
        userToFollow.setFirstName("firstName_test");
        userRepository.save(userToFollow);

        User userNotToFollow = new User("tototis@foo.com");
        userToFollow.setFirstName("firstName_test");
        userRepository.save(userNotToFollow);
    }


    @Test
    public void shouldFollowUser() {
        followingService.followUser("toto@foo.com", 2L);
        User user = userRepository.findOne(1L);
        assertThat(user.getFollowing()).hasSize(1);
    }

    @Test(expected = UserNotFoundException.class)
    public void shouldNotFollowUserIfNotExist() {
        followingService.followUser("toto@foo.com", 4L);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void shouldNotAllowUserFollowingHimSelf() {
        followingService.followUser("toto@foo.com", 1L);
    }


}
