package com.resucreator.webservices.resume;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/resumes")
public class ResumeController {
    @Autowired
    ResumeRepository repository;

    @GetMapping("")
    public ResponseEntity<Map<String, String>> getAllResumes() {
        Map<String, String> responseBody = new HashMap<>();

        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }
}
