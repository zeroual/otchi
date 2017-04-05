package com.otchi.infrastructure.config.storage;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({AmazonBlobStorageConfig.class, LocalBlobStorageConfig.class})
public class BlobStorageConfig {

}
