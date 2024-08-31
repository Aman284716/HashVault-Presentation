package com.example.HashVault.controller;


import com.example.HashVault.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public String registerUser(@RequestParam String username, @RequestParam String password) {
        try {
            userService.registerUser(username, password);
            return "User registered successfully!";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String username, @RequestParam String password) {
        try {
            if (userService.verifyUser(username, password)) {
                return "Login successful!";
            } else {
                return "Invalid username or password.";
            }
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}

