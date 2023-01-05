package com.resucreator.webservices.user;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    @Autowired
    Repository repository;

    @Autowired
    Argon2PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public String registerUser(@Valid @RequestBody User user) {
        if (repository.existsByUserName(user.getUserName())) {}

        if (repository.existsByEmail(user.getEmail())) {}

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        repository.save(user);

        return user.getId();
    }
}
