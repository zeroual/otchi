package com.otchi.infrastructure.social;

import com.otchi.domain.users.exceptions.AccountAlreadyExistsException;
import com.otchi.infrastructure.config.SocialConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.support.URIBuilder;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.view.RedirectView;

import static java.lang.String.valueOf;

@RestController
public class SocialSingUpController {
    private final Logger log = LoggerFactory.getLogger(SocialSingUpController.class);

    @Autowired
    private SocialAccountService socialAccountService;

    @Autowired
    private ProviderSignInUtils providerSignInUtils;

    @RequestMapping(value = SocialConfig.SOCIAL_SIGN_UP_URL, method = RequestMethod.GET)
    public RedirectView signUp(WebRequest webRequest, @CookieValue(value = "NG_TRANSLATE_LANG_KEY",
            required = false,
            defaultValue = "en") String langKey) {
        Connection<?> connection = providerSignInUtils.getConnectionFromSession(webRequest);
        if (connection == null) {
            log.error("Cannot create social user because connection is null");
            return redirectToView(false, null);
        }
        try {
            socialAccountService.createAccount(connection, langKey);
            return redirectToView(true, connection.getKey().getProviderId());
        } catch (AccountAlreadyExistsException e) {
            log.warn("User already exist associate the connection to this account");
            return redirectToView(false, connection.getKey().getProviderId(), "ACCOUNT_ALREADY_EXIST");
        }
    }

    private RedirectView redirectToView(boolean success, String provider) {
        return redirectToView(success, provider, null);
    }

    private RedirectView redirectToView(boolean success, String provider, String error) {
        URIBuilder uriBuilder = URIBuilder.fromUri("/#/social-register/" + provider)
                .queryParam("success", valueOf(success));
        if (error != null)
            uriBuilder.queryParam("error", error);

        return new RedirectView(uriBuilder
                .build().toString(), true);
    }
}
