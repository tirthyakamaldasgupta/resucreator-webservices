package com.resucreator.webservices.resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.resucreator.webservices.filter.JWTokenFilter;
import com.resucreator.webservices.user.User;
import com.resucreator.webservices.user.UserRepository;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/api/resumes")
public class ResumeController {
    @Autowired
    ResumeRepository resumeRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/")
    public ResponseEntity<Map<String, List<Resume>>> getAllResumes(@RequestHeader (name="Authorization") String token) {
        Map<String, List<Resume>> responseBody = new HashMap<>();

        String userName = JWTokenFilter.getUserNameFromToken(token);

        String userId = userRepository.findByUserName(userName).getId();

        List<Resume> resumes = resumeRepository.findAllByUserId(userId);

        if (resumes == null) {
            List<Resume> emptyResumes = new ArrayList<>();

            responseBody.put("resumes", emptyResumes);

            return new ResponseEntity<>(responseBody, HttpStatus.NOT_FOUND);
        }

        responseBody.put("resumes", resumes);

        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Map<String, String>> createResume(@RequestHeader (name="Authorization") String token, @Valid @RequestBody Resume newResume) {
        Map<String, String> responseBody = new HashMap<>();

        String userName = JWTokenFilter.getUserNameFromToken(token);
        User user = userRepository.findByUserName(userName);

        newResume.setUser(user);

        resumeRepository.save(newResume);

        responseBody.put("success", "The resume has been created successfully");

        return new ResponseEntity<>(responseBody, HttpStatus.CREATED);
    }
}