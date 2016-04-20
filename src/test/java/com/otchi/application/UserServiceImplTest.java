package com.otchi.application;

import com.otchi.application.impl.UserServiceImpl;
import com.otchi.domain.users.models.User;
import com.otchi.domain.users.models.UserRepository;
import com.otchi.domain.users.models.mocks.MockUserRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class UserServiceImplTest {

    private UserRepository userRepository = new MockUserRepository();
    private UserService userService = new UserServiceImpl(userRepository);

    @Before
    public void setUp() {

        User user = new User("toto@foo.com");
        user.setFirstName("firstName_test");
        userRepository.save(user);
    }

    @Test
    public void shouldFindUserByUsername() {
        Optional<User> foundUser = userService.findUserByUsername("toto@foo.com");
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getFirstName()).isEqualTo("firstName_test");
    }
}