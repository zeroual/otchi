package com.otchi.domain.users.models;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.otchi.utils.AbstractRepositoryTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class AccountRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;

    @Test
    @DatabaseSetup("/dbunit/users/users.xml")
    @Transactional
    public void shouldMapWithDatabase() {
        Account savedAccount = accountRepository.findOne(1L);
        assertThat(savedAccount).isNotNull();
        assertThat(savedAccount.getEmail()).isEqualTo("zeroual.abde@gmail.com");
        assertThat(savedAccount.getPassword()).isEqualTo("$2a$10$9m.et2mcPSzA4RdS1AOGPemNLOCerTkyEC99BUdqMSNL.4F9HUtbW");
        assertThat(savedAccount.isEnabled()).isTrue();
        assertThat(savedAccount.getUser()).isNotNull();
    }

    @Test
    @DatabaseSetup("/dbunit/users/users.xml")
    public void shouldFindUserByEmail() {
        Optional<Account> foundAccount = accountRepository.findOneByEmail("zeroual.abde@gmail.com");
        assertThat(foundAccount).isPresent();

        foundAccount = accountRepository.findOneByEmail("zeroual.abdellah@gmail.com");
        assertThat(foundAccount.isPresent()).isFalse();
    }
}