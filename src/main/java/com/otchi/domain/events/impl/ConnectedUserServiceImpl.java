package com.otchi.domain.events.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.messaging.DefaultSimpUserRegistry;

import com.otchi.domain.events.ConnectedUserService;

@Service
public class ConnectedUserServiceImpl implements ConnectedUserService{
	
	private final DefaultSimpUserRegistry defaultSimpUserRegistry ;
	
	@Autowired
    public ConnectedUserServiceImpl(DefaultSimpUserRegistry defaultSimpUserRegistry) {
		this.defaultSimpUserRegistry =defaultSimpUserRegistry;
	}

	@Override
	public boolean isConnected(String username) {
        return defaultSimpUserRegistry.getUser(username) != null ? true : false;
    }
}
