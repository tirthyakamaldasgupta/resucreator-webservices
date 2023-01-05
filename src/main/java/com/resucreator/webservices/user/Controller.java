package com.resucreator.webservices.user;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
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
    public ResponseEntity<String> registerUser(@Valid @RequestBody User user) {
        if (repository.existsByUserName(user.getUserName())) {
            return ResponseEntity.badRequest().body("This username is already taken.");
        }

        if (repository.existsByEmail(user.getEmail())) {
            return ResponseEntity.badRequest().body("This email is already taken.");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        repository.save(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(user.getId());
    }
}
