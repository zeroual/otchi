package com.otchi.api.analytics;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.otchi.application.Feed;
import com.otchi.application.FeedFetcherService;
import com.otchi.infrastructure.config.ResourcesPath;
import com.otchi.utils.AbstractIntegrationTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PostMonitorResourceTest extends AbstractIntegrationTest {

    @Autowired
    private FeedFetcherService feedFetcherService;

    @Test
    @DatabaseSetup("/dbunit/social/publications.xml")
    public void shouldIncrementViewsCount() throws Exception {
        mockMvc.perform(post(ResourcesPath.ANALYTICS + "/views/feed/1")
                .with(csrf()).contentType(contentType))
                .andExpect(status().isOk());

        Optional<Feed> feed = feedFetcherService.getFeed(1L, "");
        assertThat(feed.get().getViews()).isEqualTo(1);
    }

}
