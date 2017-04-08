package com.otchi.infrastructure.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.MimeMappings;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.inject.Inject;
import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.Arrays;
import java.util.EnumSet;

import static com.otchi.infrastructure.config.Constants.SPRING_PROFILE_PRODUCTION;

@Configuration
public class WebConfigurer implements ServletContextInitializer, EmbeddedServletContainerCustomizer {

    private final Logger log = LoggerFactory.getLogger(WebConfigurer.class);

    @Inject
    private Environment env;

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        log.info("Web application configuration, using profiles: {}", Arrays.toString(env.getActiveProfiles()));
        EnumSet<DispatcherType> disps = EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.ASYNC);
        if (env.acceptsProfiles(SPRING_PROFILE_PRODUCTION)) {
            initStaticResourcesProductionFilter(servletContext, disps);
        }
        log.info("Web application fully configured");
    }

    /**
     * Initializes the static resources production Filter.
     */
    private void initStaticResourcesProductionFilter(ServletContext servletContext,
                                                     EnumSet<DispatcherType> disps) {

        log.debug("Registering static resources production Filter");
        FilterRegistration.Dynamic staticResourcesProductionFilter =
                servletContext.addFilter("staticResourcesProductionFilter",
                        new StaticResourcesProductionFilter());

        staticResourcesProductionFilter.addMappingForUrlPatterns(disps, true, "/");
        staticResourcesProductionFilter.addMappingForUrlPatterns(disps, true, "/index.html");
        staticResourcesProductionFilter.addMappingForUrlPatterns(disps, true, "/assets/*");
        staticResourcesProductionFilter.addMappingForUrlPatterns(disps, true, "/scripts/*");
        staticResourcesProductionFilter.setAsyncSupported(true);
    }

    @Override
    public void customize(ConfigurableEmbeddedServletContainer container) {
        MimeMappings mappings = new MimeMappings(MimeMappings.DEFAULT);
        mappings.add("html", "text/html;charset=utf-8");
        mappings.add("json", "text/html;charset=utf-8");
        container.setMimeMappings(mappings);
    }


}
