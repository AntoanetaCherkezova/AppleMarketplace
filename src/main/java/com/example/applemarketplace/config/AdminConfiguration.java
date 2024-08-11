package com.example.applemarketplace.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class AdminConfiguration {
    @Value("${admin.username}")
    private String username;

    @Value("${admin.password}")
    private String password;

    @Value("${admin.first-name}")
    private String firstName;

    @Value("${admin.last-name}")
    private String lastName;

    @Value("${admin.email}")
    private String email;

    @Value("${admin.age}")
    private int age;

    @Value("${admin.city}")
    private String city;

    @Value("${admin.phone-number}")
    private String phoneNumber;

}
