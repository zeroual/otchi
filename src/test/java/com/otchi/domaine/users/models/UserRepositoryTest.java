package com.otchi.domaine.users.models;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.otchi.utils.AbstractRepositoryTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class UserRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DatabaseSetup("/dbunit/users/users.xml")
    @Transactional
    public void shouldMapWithDatabase() {
        User savedUser = userRepository.findOne("1");
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getEmail()).isEqualTo("zeroual.abde@gmail.com");
        assertThat(savedUser.getFirstName()).isEqualTo("Abdellah");
        assertThat(savedUser.getLastName()).isEqualTo("ZEROUAL");
    }

    @Test
    @DatabaseSetup("/dbunit/users/users.xml")
    public void shouldFindUserByEmail() {
        Optional<User> foundUser = userRepository.findOneByEmail("zeroual.abde@gmail.com");
        assertThat(foundUser).isPresent();

        foundUser = userRepository.findOneByEmail("zeroual.abdellah@gmail.com");
        assertThat(foundUser.isPresent()).isFalse();
    }
}