package com.otchi.infrastructure.config;

import com.otchi.application.AccountService;
import com.otchi.infrastructure.social.CustomSignInAdapter;
import com.otchi.infrastructure.social.SocialAccountService;
import com.otchi.infrastructure.social.SocialSingUpController;
import com.otchi.infrastructure.utils.FileUtilsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurer;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInController;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.google.connect.GoogleConnectionFactory;
import org.springframework.social.security.AuthenticationNameUserIdSource;
import org.springframework.social.twitter.connect.TwitterConnectionFactory;

import javax.sql.DataSource;

@Configuration
@EnableSocial
public class SocialConfig implements SocialConfigurer {
    private final Logger log = LoggerFactory.getLogger(SocialConfig.class);

    @Autowired
    DataSource dataSource;

    @Value("${spring.social.google.clientId}")
    private String googleClientId;

    @Value("${spring.social.google.clientSecret}")
    private String googleClientSecret;

    @Value("${spring.social.facebook.clientId}")
    private String facebookClientId;

    @Value("${spring.social.facebook.clientSecret}")
    private String facebookClientSecret;

    @Value("${spring.social.twitter.clientId}")
    private String twitterClientId;

    @Value("${spring.social.twitter.clientSecret}")
    private String twitterClientSecret;

    @Autowired
    private AccountService accountService;

    public static final String SOCIAL_SIGN_UP_URL = "/social/signup";


    @Override
    public void addConnectionFactories(ConnectionFactoryConfigurer connectionFactoryConfigurer, Environment environment) {
        if (googleClientId != null && googleClientSecret != null) {
            log.debug("Configuring GoogleConnectionFactory");
            GoogleConnectionFactory connectionFactory = new GoogleConnectionFactory(googleClientId, googleClientSecret);
            connectionFactoryConfigurer.addConnectionFactory(connectionFactory);
        } else {
            log.error("Cannot configure GoogleConnectionFactory id or secret null");
        }

        if (facebookClientId != null && facebookClientSecret != null) {
            log.debug("Configuring FacebookConnectionFactory");
            FacebookConnectionFactory connectionFactory = new FacebookConnectionFactory(facebookClientId, facebookClientSecret);
            connectionFactoryConfigurer.addConnectionFactory(connectionFactory);
        } else {
            log.error("Cannot configure FacebookConnectionFactory id or secret null");
        }

        if (twitterClientId != null && twitterClientSecret != null) {
            log.debug("Configuring TwitterConnectionFactory");
            TwitterConnectionFactory connectionFactory = new TwitterConnectionFactory(twitterClientId, twitterClientSecret);
            connectionFactoryConfigurer.addConnectionFactory(connectionFactory);
        } else {
            log.error("Cannot configure TwitterConnectionFactory id or secret null");
        }
    }

    @Override
    public UserIdSource getUserIdSource() {
        return new AuthenticationNameUserIdSource();
    }

    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        return new JdbcUsersConnectionRepository(dataSource, connectionFactoryLocator, Encryptors.noOpText());
    }

    @Bean
    public SignInAdapter signInAdapter() {
        return new CustomSignInAdapter();
    }

    @Bean
    public ProviderSignInController providerSignInController(ConnectionFactoryLocator connectionFactoryLocator,
                                                             UsersConnectionRepository usersConnectionRepository,
                                                             SignInAdapter signInAdapter){
        ProviderSignInController providerSignInCtrl = new ProviderSignInController(connectionFactoryLocator, usersConnectionRepository, signInAdapter);
        providerSignInCtrl.setSignUpUrl(SOCIAL_SIGN_UP_URL);
        return providerSignInCtrl;
    }

    @Bean
    public ProviderSignInUtils getProviderSignInUtils(ConnectionFactoryLocator connectionFactoryLocator,
                                                      UsersConnectionRepository usersConnectionRepository) {
        return new ProviderSignInUtils(connectionFactoryLocator, usersConnectionRepository);
    }

    @Bean
    SocialSingUpController socialController() {
        return new SocialSingUpController();
    }

    @Bean
    SocialAccountService socialService(UsersConnectionRepository usersConnectionRepository, FileUtilsServiceImpl fileUtilsService) {
        return new SocialAccountService(accountService, usersConnectionRepository, fileUtilsService);
    }
}
