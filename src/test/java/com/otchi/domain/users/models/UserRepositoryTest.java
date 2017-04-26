package com.otchi.domain.users.models;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.otchi.utils.AbstractIntegrationTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class UserRepositoryTest extends AbstractIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DatabaseSetup("/dbunit/users/users.xml")
    @Transactional
    public void shouldMapWithDatabase() {
        User savedUser = userRepository.findOne(2L);
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getEmail()).isEqualTo("mr.jaifar@gmail.com");
        assertThat(savedUser.getFirstName()).isEqualTo("Reda");
        assertThat(savedUser.getLastName()).isEqualTo("JAIFAR");
        assertThat(savedUser.picture()).isEqualTo("http://host.com/image.png");

    }

    @Test
    @DatabaseSetup("/dbunit/users/users.xml")
    public void shouldFindUserByUsername() {
        Optional<User> foundUser = userRepository.findOneByUsername("zeroual.abde@gmail.com");
        assertThat(foundUser).isPresent();

        foundUser = userRepository.findOneByUsername("zeroual.abdellah@gmail.com");
        assertThat(foundUser.isPresent()).isFalse();
    }

    @Test
    @DatabaseSetup("/dbunit/users/users.xml")
    public void shouldReturnAllUserExcludingSpecifiedOne() {
        List<User> users = userRepository.findAllByIdNotLike(1L);
        assertThat(users).hasSize(1);
        assertThat((users)).extracting(user -> user.getId()).doesNotContain(1L);
    }

    @Test
    @DatabaseSetup("/dbunit/users/users.xml")
    public void shouldReturnAllUserExcludingSpecifiedList() {

        List<Long> ids = new LinkedList<Long>();
        ids.add(1L);
        ids.add(2L);

        List<User> users = userRepository.findAllByIdNotIn(ids);
        assertThat(users).hasSize(0);
        assertThat((users)).extracting(user -> user.getId()).doesNotContain(1L);
        assertThat((users)).extracting(user -> user.getId()).doesNotContain(2L);
    }
}
