package com.otchi.infrastructure.config.storage;

import com.otchi.infrastructure.storage.BlobStorageService;
import com.otchi.infrastructure.stubs.LocalBlobStorageService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.servlet.ServletContext;

import static com.otchi.infrastructure.config.Constants.SPRING_PROFILE_DEVELOPMENT;

@Configuration
@Profile(SPRING_PROFILE_DEVELOPMENT)

public class LocalBlobStorageConfig {

    @Bean(destroyMethod = "removeTStorageFolder")
    public BlobStorageService blobStorageServiceStub(ServletContext servletContext) {
        return new LocalBlobStorageService(servletContext);
    }

}
