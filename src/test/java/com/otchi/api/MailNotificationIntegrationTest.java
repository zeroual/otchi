package com.otchi.api;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.otchi.infrastructure.config.ResourcesPath;
import com.otchi.utils.AbstractIntegrationTest;
import org.junit.Test;
import org.jvnet.mock_javamail.Mailbox;

import javax.mail.internet.AddressException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MailNotificationIntegrationTest extends AbstractIntegrationTest {


    @Test
    @DatabaseSetup("/dbunit/social/stream-feeds.xml")
    public void shouldSendMailNotificationToPostAuthor() throws Exception {
        likePost();
        verifyThatMailNotificationWasSent();
    }

    private void verifyThatMailNotificationWasSent() throws AddressException {

        Mailbox mailbox = Mailbox.get("zeroual.abde@gmail.com");
        assertThat(mailbox.isEmpty()).isFalse();
    }

    private void likePost() throws Exception {
        mockMvc.perform(post(ResourcesPath.FEED + "/1/like")
                .with(user("brahim.yakoubi@gmail.com"))
                .with(csrf()).contentType(contentType))
                .andExpect(status().isOk());

    }
}
