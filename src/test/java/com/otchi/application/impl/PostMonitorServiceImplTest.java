package com.otchi.application.impl;

import com.otchi.application.PostMonitorService;
import com.otchi.domain.analytics.PostView;
import com.otchi.domain.analytics.PostViewRepository;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


public class PostMonitorServiceImplTest {

    private PostViewRepository postViewRepository = mock(PostViewRepository.class);
    private PostMonitorService postMonitorService = new PostMonitorServiceImpl(postViewRepository);


    @Test
    public void shouldIncrementPosViewsByTwo() {
        when(postViewRepository.countByViewPostId(12L)).thenReturn(83);
        PostView postView = new PostView(12L, "92:91:13:5e:16:d8");
        Integer views = postMonitorService.incrementViews(postView);
        verify(postViewRepository).save(postView);
        assertThat(views).isEqualTo(83);
    }

    @Test
    public void shouldGetViewsCountOfFeed(){
        when(postViewRepository.countByViewPostId(12L)).thenReturn(83);
        Integer viewsCount = postMonitorService.getViewsCountOf(12L);
        assertThat(viewsCount).isEqualTo(83);
    }
}
