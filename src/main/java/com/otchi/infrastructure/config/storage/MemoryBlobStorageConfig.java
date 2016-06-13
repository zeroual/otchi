package com.otchi.infrastructure.config.storage;

import com.otchi.infrastructure.storage.BlobStorageService;
import com.otchi.infrastructure.stubs.MockBlobStorageService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import static com.otchi.infrastructure.config.Constants.SPRING_PROFILE_DEVELOPMENT;

@Configuration
@Profile(SPRING_PROFILE_DEVELOPMENT)

public class MemoryBlobStorageConfig {

    @Bean
    public BlobStorageService blobStorageServiceStub() {
        return new MockBlobStorageService();
    }

}
