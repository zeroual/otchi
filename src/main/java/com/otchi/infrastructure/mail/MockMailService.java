package com.otchi.infrastructure.mail;

import com.otchi.application.MailService;
import com.otchi.domain.users.models.User;

public class MockMailService implements MailService {

	@Override
	public void sendWelcomeEmail(User user) {

	}

	public void sendEmail(User user) {

	}
}
