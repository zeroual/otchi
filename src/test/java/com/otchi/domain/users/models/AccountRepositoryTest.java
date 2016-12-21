package com.otchi.domain.users.models;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.otchi.utils.AbstractIntegrationTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class AccountRepositoryTest extends AbstractIntegrationTest {

    @Autowired
    private AccountRepository accountRepository;

    @Test
    @DatabaseSetup("/dbunit/users/users.xml")
    public void shouldMapWithDatabase() {
        Account savedAccount = accountRepository.findOne(1L);
        assertThat(savedAccount).isNotNull();
        assertThat(savedAccount.getUsername()).isEqualTo("zeroual.abde@gmail.com");
        assertThat(savedAccount.getPassword()).isEqualTo("$2a$10$9m.et2mcPSzA4RdS1AOGPemNLOCerTkyEC99BUdqMSNL.4F9HUtbW");
        assertThat(savedAccount.isEnabled()).isTrue();
        assertThat(savedAccount.getUser()).isNotNull();
        assertThat(savedAccount.getUser().getFirstName()).isEqualTo("Abdellah");
        assertThat(savedAccount.getUser().getLastName()).isEqualTo("ZEROUAL");
        assertThat(savedAccount.getUser().getUsername()).isEqualTo("zeroual.abde@gmail.com");
        assertThat(savedAccount.getUser().getId()).isEqualTo(1L);
    }

    @Test
    @DatabaseSetup("/dbunit/users/users.xml")
    public void shouldFindAccountByUsername() {
        Optional<Account> foundAccount = accountRepository.findOneByUsername("zeroual.abde@gmail.com");
        assertThat(foundAccount).isPresent();

        foundAccount = accountRepository.findOneByUsername("zeroual.abdellah@gmail.com");
        assertThat(foundAccount.isPresent()).isFalse();
    }
}
