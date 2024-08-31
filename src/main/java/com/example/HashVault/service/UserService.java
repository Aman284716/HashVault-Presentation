package com.example.HashVault.service;


import com.example.HashVault.model.User;
import com.example.HashVault.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Optional;
import java.security.MessageDigest;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private static final SecureRandom random = new SecureRandom();

    public User registerUser(String username, String password) throws Exception {
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String saltString = Base64.getEncoder().encodeToString(salt);

        String hashedPassword = hashPassword(password, saltString);

        User user = new User();
        user.setUsername(username);
        user.setHashedPassword(hashedPassword);
        user.setSalt(saltString);
        return userRepository.save(user);
    }

    public boolean verifyUser(String username, String password) throws Exception {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            String hashedPassword = hashPassword(password, user.getSalt());
            return hashedPassword.equals(user.getHashedPassword());
        }
        return false;
    }

    private String hashPassword(String password, String salt) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update((password + salt).getBytes());
        byte[] hashedBytes = md.digest();
        return Base64.getEncoder().encodeToString(hashedBytes);
    }
}
