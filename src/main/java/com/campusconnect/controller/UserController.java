package com.campusconnect.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.campusconnect.model.User;
import com.campusconnect.repository.UserRepository;

@RestController
public class UserController {

    @Autowired
    private UserRepository repo;

    @GetMapping("/add")
    public String addUser() {
        User user = new User();

        user.setName("Roushan");
        user.setEmail("test@gmail.com");
        user.setPassword("1234");
        user.setRole("STUDENT");

        repo.save(user);

        return "User saved successfully!";
    }
}