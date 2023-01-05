package com.resucreator.webservices.user;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    @Autowired
    Repository repository;

    @PostMapping("/register")
    public String registerUser(@Valid @RequestBody User user) {
        repository.save(user);
        // System.out.println(user.getFirstName());
        return "OK";
    }
}
