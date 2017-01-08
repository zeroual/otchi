package com.otchi.domain.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.messaging.DefaultSimpUserRegistry;

public class ConnectedUserService {
	
	private final DefaultSimpUserRegistry defaultSimpUserRegistry;
	
	@Autowired
    public ConnectedUserService(DefaultSimpUserRegistry defaultSimpUserRegistry) {
		this.defaultSimpUserRegistry = defaultSimpUserRegistry;
	}


	public boolean isConnected(String username) {
        return defaultSimpUserRegistry.getUser(username) != null ? true : false;
    }
}
