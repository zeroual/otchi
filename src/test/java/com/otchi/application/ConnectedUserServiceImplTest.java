package com.otchi.application;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.web.socket.messaging.DefaultSimpUserRegistry;

import com.otchi.application.impl.ConnectedUserServiceImpl;
import com.otchi.domain.users.models.User;

import cucumber.api.java.en.When;

public class ConnectedUserServiceImplTest {

	private final DefaultSimpUserRegistry simpUserRegistry = Mockito.mock(DefaultSimpUserRegistry.class);
	private final SimpUser user = Mockito.mock(SimpUser.class);
	private ConnectedUserService connectedUserService;
	
	@Before
	public void init(){
		
		connectedUserService = new ConnectedUserServiceImpl(simpUserRegistry);
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void isUserConnectedTrueTest(){
		
		String username = "toto";
		Mockito.when(simpUserRegistry.getUser(username)).thenReturn(user);

		
		final Boolean actual = connectedUserService.isUserConnected(username);
		
		Mockito.verify(simpUserRegistry).getUser(username);
		Assert.assertTrue(actual);
	}
	
	@Test
	public void isUserConnectedFalseTest(){
		
		String username = "toto";
		Mockito.when(simpUserRegistry.getUser(username)).thenReturn(null);

		final Boolean actual = connectedUserService.isUserConnected(username);

		Mockito.verify(simpUserRegistry).getUser(username);
		
		Assert.assertEquals(actual, false);
	}
}
