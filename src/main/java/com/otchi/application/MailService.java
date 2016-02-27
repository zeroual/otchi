package com.otchi.application;

import com.otchi.domaine.users.models.User;

public interface MailService {

    void sendWelcomeEmail(User user);
}
