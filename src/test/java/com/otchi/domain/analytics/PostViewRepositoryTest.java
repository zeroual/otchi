package com.otchi.domain.analytics;

import com.otchi.utils.AbstractIntegrationTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class PostViewRepositoryTest extends AbstractIntegrationTest {

    @Autowired
    private PostViewRepository postViewRepository;

    @Test
    public void shouldSavePostViewOnce() {
        PostView view1 = new PostView(2L, "92:91:13:5e:16:d8");
        PostView view2 = new PostView(2L, "92:91:13:5e:16:d8");

        postViewRepository.save(view1);
        postViewRepository.save(view2);
        assertThat(postViewRepository.countByViewPostId(2L)).isEqualTo(1);
    }
}
