package com.resucreator.webservices;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/api/auth/register").permitAll().and().authorizeRequests();
        http.authorizeRequests().antMatchers("/api/auth/authenticate").permitAll().and().authorizeRequests();
        http.csrf().disable();

        return http.build();
    }
}
