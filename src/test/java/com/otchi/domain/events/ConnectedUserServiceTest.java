package com.otchi.domain.events;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.web.socket.messaging.DefaultSimpUserRegistry;

public class ConnectedUserServiceTest {

	private DefaultSimpUserRegistry defaultSimpUserRegistry = mock(DefaultSimpUserRegistry.class);
	private SimpUser simpUser = mock(SimpUser.class);
	private ConnectedUserService connectedUserService = new ConnectedUserService(defaultSimpUserRegistry);
	
	@Test
	public void isConnectedTest_true(){
		
		String username = "username";
		// expect
		when(defaultSimpUserRegistry.getUser(username)).thenReturn(simpUser);
		
		// Action
		Boolean isConnected = connectedUserService.isConnected(username);
        assertThat(isConnected).isTrue();

	}
	
	@Test
	public void isConnectedTest_false(){
		
		String username = "username";
		// expect
		when(defaultSimpUserRegistry.getUser(username)).thenReturn(null);
		
		// Action
		Boolean isConnected = connectedUserService.isConnected(username);
        assertThat(isConnected).isFalse();

	}
}
