package com.resucreator.webservices.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.resucreator.webservices.filter.JwtTokenFilter;

@Configuration
public class SecurityConfiguration {
    @Bean
    public DefaultSecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/api/auth/register", "/api/auth/authenticate").permitAll()
            .and().authorizeRequests();
        http.addFilterBefore(new JwtTokenFilter(), BasicAuthenticationFilter.class).antMatcher("/api/resumes**");
        http.csrf().disable();
    
        return http.build();
    }    
}
