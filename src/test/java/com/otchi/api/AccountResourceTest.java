package com.otchi.api;

import com.otchi.api.facades.dto.AccountDTO;
import com.otchi.domain.users.models.Account;
import com.otchi.domain.users.models.AccountRepository;
import com.otchi.infrastructure.config.ResourcesPath;
import com.otchi.utils.AbstractControllerTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AccountResourceTest extends AbstractControllerTest {

    @Autowired
    private AccountRepository accountRepository;

    @Test
    public void shouldCreateAccount() throws Exception {
        AccountDTO newAccount = new AccountDTO("Abdel", "ZEROS", "zeros@gmail.com", "password", "en");
        mockMvc.perform(post(ResourcesPath.REGISTER).with(csrf())
                .content(json(newAccount))
                .contentType(contentType))
                .andExpect(status().isCreated());
        Optional<Account> createdAccount = accountRepository.findOneByUsername("zeros@gmail.com");
        assertThat(createdAccount).isPresent();
        assertThat(createdAccount.get().getUser().getFirstName()).isEqualTo("Abdel");
    }

    @Test
    public void shouldReturn409IfAccountAlreadyExistWithSameMail() throws Exception {
        Account existingAccount = new Account("Amin", "jabri", "zeros@gmail.com", "password", "en");
        accountRepository.save(existingAccount);
        AccountDTO newAccount = new AccountDTO("Abdel", "ZEROS", "zeros@gmail.com", "password", "en");
        mockMvc.perform(post(ResourcesPath.REGISTER).with(csrf())
                .content(json(newAccount))
                .contentType(contentType))
                .andExpect(status().isConflict());
    }
}