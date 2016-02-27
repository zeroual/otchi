package com.otchi.infrastructure.mail;

import com.otchi.application.MailService;
import com.otchi.domaine.users.models.User;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {
    @Override
    public void sendWelcomeEmail(User user) {

    }
}
