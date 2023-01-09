package com.resucreator.webservices.user;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/api/auth")
public class UserController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    Argon2PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerUser(@Valid @RequestBody User user) {
        Map<String, String> responseBody = new HashMap<>();

        if (userRepository.existsByUserName(user.getUserName())) {
            responseBody.put("error", "That username is already taken.");

            return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            responseBody.put("error", "That email is already taken.");

            return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);

        responseBody.put("success", "The user has been created.");

        return new ResponseEntity<>(responseBody, HttpStatus.CREATED);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<Map<String, Object>> loginUser(@RequestBody User returningUser) {
        Map<String, Object> responseBody = new HashMap<>();

        String returningUserUserName = returningUser.getUserName();

        if (userRepository.existsByUserName(returningUserUserName)) {
            User user = userRepository.findByUserName(returningUserUserName);

            if (passwordEncoder.matches(returningUser.getPassword(), user.getPassword())) {
                Integer jwtExpiration = 3600;

                Long jwtExpirationInMilliSeconds = System.currentTimeMillis() + (jwtExpiration * 1000);

                String jsonWebToken = Jwts.builder()
                    .setSubject(user.getUserName())
                    .setExpiration(new Date(jwtExpirationInMilliSeconds))
                    .claim("userName", user.getUserName())
                    .claim("email", user.getEmail())
                    .signWith(SignatureAlgorithm.HS256, "secret")
                    .compact();

                responseBody.put("token_type", "bearer");
                responseBody.put("access_token", jsonWebToken);
                responseBody.put("expires_in", jwtExpiration);

                return new ResponseEntity<>(responseBody, HttpStatus.OK);
            }

            responseBody.put("error", "Please provide the correct password.");

            return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
        }

        responseBody.put("error", "No user exists with the particular username.");

        return new ResponseEntity<>(responseBody, HttpStatus.NOT_FOUND);
    }
}
