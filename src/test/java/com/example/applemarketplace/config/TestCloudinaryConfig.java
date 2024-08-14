package com.example.applemarketplace.config;
import com.example.applemarketplace.service.CloudinaryService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Configuration
@Profile("test")
public class TestCloudinaryConfig {

    @Value("${cloudinary.name}")
    private String cloudName;

    @Value("${cloudinary.api-key}")
    private String apiKey;

    @Value("${cloudinary.api-secret}")
    private String apiSecret;

    @Bean
    public CloudinaryService cloudinaryService() {
        return new CloudinaryService(cloudName, apiKey, apiSecret) {
            @Override
            public String saveImage(MultipartFile multipartFile) {
                return UUID.randomUUID().toString();
            }
        };
    }
}