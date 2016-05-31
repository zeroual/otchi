package com.otchi.infrastructure.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;
import com.otchi.infrastructure.storage.AmazonBlobStorageService;
import com.otchi.infrastructure.storage.BlobStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BlobStorageConfig {

    @Value("${amazon.aws.access-key-id}")
    private  String accessKey;

    @Value("${amazon.aws.access-key-secret}")
    private  String secretKey;

    @Bean
    public AmazonS3Client amazonS3Client() {
        AWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
        AmazonS3Client amazonS3Client = new AmazonS3Client(awsCredentials);
        amazonS3Client.setRegion(Region.getRegion(Regions.EU_CENTRAL_1));
        return amazonS3Client;
    }

    @Bean
    public BlobStorageService blobStorageService(AmazonS3Client amazonS3Client) {
        return new AmazonBlobStorageService(amazonS3Client);
    }
}