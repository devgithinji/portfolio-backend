package com.densoft.portfolio.config;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class Config {

    @Value("${aws.accessKeyId}")
    private String awsAccessKeyId;

    @Value("${aws.secretAccessKey}")
    private String awsSecretKeyId;

    @Bean
    public S3Client s3Client() {

        return S3Client.
                builder().
                credentialsProvider(
                        StaticCredentialsProvider.
                                create(AwsBasicCredentials.
                                        create(awsAccessKeyId, awsSecretKeyId))
                ).
                region(Region.US_EAST_1).build();
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }


}
