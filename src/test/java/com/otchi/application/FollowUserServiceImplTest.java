package com.otchi.application;

import com.otchi.application.impl.FollowUserServiceImpl;
import com.otchi.domaine.users.exceptions.UserNotFoundException;
import com.otchi.domaine.users.models.User;
import com.otchi.domaine.users.models.UserRepository;
import com.otchi.domaine.users.models.mocks.MockUserRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class FollowUserServiceImplTest {

    private UserRepository userRepository = new MockUserRepository();
    private FollowUserService followUserService = new FollowUserServiceImpl(userRepository);


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
        followUserService.followUser("toto@foo.com", 2L);
        User user = userRepository.findOne(1L);
        assertThat(user.getFollowing()).hasSize(1);
    }

    @Test(expected = UserNotFoundException.class)
    public void shouldNotFollowUserIfNotExist() {
        followUserService.followUser("toto@foo.com", 4L);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void shouldNotAllowUserFollowingHimSelf() {
        followUserService.followUser("toto@foo.com", 1L);
    }

    @Test
    public void shouldReturnAllPossibleFollowersDiscludingUser() {
        List<User> followerList = followUserService.getAllPossibleFollowers("toto@foo.com");
        assertThat(followerList).hasSize(1);
    }


}
