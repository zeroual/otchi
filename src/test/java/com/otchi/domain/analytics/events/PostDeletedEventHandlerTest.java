package com.otchi.domain.analytics.events;

import com.otchi.domain.analytics.PostViewRepository;
import com.otchi.domain.social.events.PostDeletedEvent;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class PostDeletedEventHandlerTest {

    private PostViewRepository postViewRepository = mock(PostViewRepository.class);
    private PostDeletedEventHandler postDeletedEventHandler = new PostDeletedEventHandler(postViewRepository);

    @Test
    public void shouldRemovePostViews() {
        PostDeletedEvent postDeletedEvent = new PostDeletedEvent(12L);
        postDeletedEventHandler.removePostViewsCount(postDeletedEvent);
        verify(postViewRepository).deleteByViewPostId(12L);
    }
}