package com.otchi.domain.events;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.web.socket.messaging.DefaultSimpUserRegistry;

import com.otchi.domain.events.impl.ConnectedUserServiceImpl;

public class ConnectedUserServiceImplTest {

	private DefaultSimpUserRegistry defaultSimpUserRegistry = mock(DefaultSimpUserRegistry.class);
	private SimpUser simpUser = mock(SimpUser.class);
	private ConnectedUserServiceImpl connectedUserService = new ConnectedUserServiceImpl(defaultSimpUserRegistry);
	
	@Test
	public void isConnectedTest_UserIsConnected(){
		
		String username = "username";
		// expect
		when(defaultSimpUserRegistry.getUser(username)).thenReturn(simpUser);
		
		// Action
		Boolean isConnected = connectedUserService.isConnected(username);
        assertThat(isConnected).isTrue();

	}
	
	@Test
	public void isConnectedTest_UserIsNotConnected(){
		
		String username = "username";
		// expect
		when(defaultSimpUserRegistry.getUser(username)).thenReturn(null);
		
		// Action
		Boolean isConnected = connectedUserService.isConnected(username);
        assertThat(isConnected).isFalse();

	}
}
