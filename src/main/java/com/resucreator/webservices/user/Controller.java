package com.resucreator.webservices.user;

import java.util.HashMap;
import java.util.Map;

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
    public ResponseEntity<Map<String, String>> registerUser(@Valid @RequestBody User user) {
        Map<String, String> body = new HashMap<>();
        
        if (repository.existsByUserName(user.getUserName())) {
            body.put("error", "This username is already taken.");

            return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
        }

        if (repository.existsByEmail(user.getEmail())) {
            body.put("error", "This email is already taken.");

            return new ResponseEntity<>(body, HttpStatus.BAD_GATEWAY);
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        repository.save(user);

        body.put("success", "The user has been created.");

        return new ResponseEntity<>(body, HttpStatus.CREATED);
    }
}
