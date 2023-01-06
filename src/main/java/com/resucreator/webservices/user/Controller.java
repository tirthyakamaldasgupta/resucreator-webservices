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
        Map<String, String> responseBody = new HashMap<>();
        
        if (repository.existsByUserName(user.getUserName())) {
            responseBody.put("error", "This username is already taken.");

            return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
        }

        if (repository.existsByEmail(user.getEmail())) {
            responseBody.put("error", "This email is already taken.");

            return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        repository.save(user);

        responseBody.put("success", "The user has been created.");

        return new ResponseEntity<>(responseBody, HttpStatus.CREATED);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<Map<String, String>> loginUser(@RequestBody User returningUser) {
        Map<String, String> responseBody = new HashMap<>();

        String returningUserUserName = returningUser.getUserName();

        if (repository.existsByUserName(returningUserUserName)) {
            User user = repository.findByUserName(returningUserUserName);

            if (passwordEncoder.matches(user.getPassword(), passwordEncoder.encode(returningUser.getPassword()))) {
                return new ResponseEntity<>(responseBody, HttpStatus.FOUND);
            }
            
            responseBody.put("error", "Please provide the correct password.");

            return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
        }

        responseBody.put("error", "No user exists with the particular username.");

        return new ResponseEntity<>(responseBody, HttpStatus.NOT_FOUND);
    }
}
