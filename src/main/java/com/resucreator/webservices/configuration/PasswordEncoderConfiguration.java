package com.resucreator.webservices.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

@Configuration
public class PasswordEncoderConfiguration {
    @Bean
    public Argon2PasswordEncoder getPasswordEncoder() {
        return new Argon2PasswordEncoder();
    }
}
