package com.otchi.infrastructure.mail;

import com.otchi.application.MailService;
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

    @Autowired
    public MailServiceImpl(JavaMailSender mailSender, SpringTemplateEngine templateEngine, String noReplyMail) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
        this.noReplyMail = noReplyMail;
    }

    @Override
    public void sendWelcomeEmail(User user) {
        log.debug("Sending welcome e-mail to '{}'", user.getEmail());
        Context context = new Context();
        String content = templateEngine.process("welcomeEmail", context);
        String subject = "Welcome to otchi";
        sendEmail(user.getEmail(), subject, content);
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

	@Override
	public void sendEmail(User user) {
		log.debug("Sending e-mail to '{}'", user.getEmail());
    Context context = new Context();
    String content = templateEngine.process("welcomeEmail", context);
    String subject = "xxxxxxxxx";
    sendEmail(user.getEmail(), subject, content);
		
	}

}
