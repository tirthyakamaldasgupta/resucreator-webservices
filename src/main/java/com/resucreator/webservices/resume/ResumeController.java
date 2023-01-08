package com.resucreator.webservices.resume;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.resucreator.webservices.user.User;
import com.resucreator.webservices.user.UserRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@RestController
@RequestMapping("/api/resumes")
public class ResumeController {
    @Autowired
    ResumeRepository resumeRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping("")
    public ResponseEntity<Map<String, String>> getAllResumes(@RequestHeader (name="Authorization") String token) {
        Map<String, String> responseBody = new HashMap<>();

        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }
}