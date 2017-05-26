package com.otchi.infrastructure.mail;

import com.otchi.application.MailService;
import com.otchi.domain.notifications.services.MailNotification;
import com.otchi.domain.users.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import javax.mail.internet.MimeMessage;

@Service
public class MailServiceImpl implements MailService {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;
    private static final Logger log = LoggerFactory.getLogger(MailServiceImpl.class);
    private final String noReplyMail;
    private String appUrl;

    @Autowired
    public MailServiceImpl(JavaMailSender mailSender,
                           SpringTemplateEngine templateEngine,
                           String noReplyMail,
                           String appUrl) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
        this.noReplyMail = noReplyMail;
        this.appUrl = appUrl;
    }

    @Override
    public void sendWelcomeEmail(User user) {
        log.debug("Sending welcome e-mail to '{}'", user.getEmail());
        Context context = new Context();
        String content = templateEngine.process("welcomeEmail", context);
        String subject = "Welcome to otchi";
        sendEmail(user.getEmail(), subject, content);
    }

    @Override
    public void sendNotificationMail(MailNotification mailNotification) {
        Context context = new Context();
        context.setVariable("notification",mailNotification);
        context.setVariable("appUrl",appUrl);
        String content = templateEngine.process("notificationMail", context);
        String subject = "Otchi notifications";
        sendEmail(mailNotification.getTo().getEmail(), subject, content);
    }

    public void sendEmail(String to, String subject, String content) {
        log.debug("Send e-mail to '{}' with subject '{}'", to, subject);

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            boolean isMultipart = false;
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, isMultipart, "UTF-8");
            message.setTo(to);
            message.setFrom(noReplyMail);
            message.setSubject(subject);
            boolean isHTML = true;
            message.setText(content, isHTML);
            mailSender.send(mimeMessage);
            log.debug("Sent e-mail to User '{}'", to);
        } catch (Exception e) {
            log.warn("E-mail could not be sent to user '{}', exception is: {}", to, e.getMessage());
        }
    }

}
