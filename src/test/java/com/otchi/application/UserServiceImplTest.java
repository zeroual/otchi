package com.otchi.application;

import com.otchi.application.impl.UserServiceImpl;
import com.otchi.domain.analytics.ProfileViewRepository;
import com.otchi.domain.users.models.User;
import com.otchi.domain.users.models.UserRepository;
import com.otchi.domain.users.models.mocks.MockUserRepository;
import com.otchi.utils.mocks.MockCrudRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class UserServiceImplTest {

    private UserRepository userRepository = new MockUserRepository();
    private UserService userService = new UserServiceImpl(userRepository);

    @Before
    public void setUp() {

        MockCrudRepository.clearDatabase();
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

    @Test
    public void shouldFindUserById() {
        Optional<User> foundUser = userService.findUserById(1L);
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getFirstName()).isEqualTo("firstName_test");
    }
    
}