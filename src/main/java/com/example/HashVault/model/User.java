package com.example.HashVault.model;


import jakarta.persistence.*;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    @Lob
    private String hashedPassword;

    @Lob
    private String salt;

}
