package com.otchi.application.impl;

import org.apache.catalina.ssi.SSIMediator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.messaging.DefaultSimpUserRegistry;

import com.google.common.base.Preconditions;
import com.otchi.application.ConnectedUserService;

@Component
public class ConnectedUserServiceImpl implements ConnectedUserService {

	private final SimpUserRegistry simpUserRegistry;
	
	@Autowired
	public ConnectedUserServiceImpl(SimpUserRegistry simpUserRegistry) {
		this.simpUserRegistry = Preconditions.checkNotNull(simpUserRegistry);
	}

	@Override
	public Boolean isUserConnected(String username) {

		if(simpUserRegistry.getUser(username) == null){
			return false;
		} else{
			return true;
		}
	}

}
