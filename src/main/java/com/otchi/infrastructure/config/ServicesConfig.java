package com.otchi.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.messaging.DefaultSimpUserRegistry;

@Configuration
@ComponentScan({"com.otchi.application", "com.otchi.domain"})
public class ServicesConfig {

	@Bean
	public DefaultSimpUserRegistry defaultSimpUserRegistry(){
		return new DefaultSimpUserRegistry();
	}
}
