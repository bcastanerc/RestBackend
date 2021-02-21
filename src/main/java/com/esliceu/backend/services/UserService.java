package com.esliceu.backend.services;

import com.esliceu.backend.entities.User;

import java.security.NoSuchAlgorithmException;

public interface UserService {
    User findByEmailEquals(String email);
    User findById(Long user_id);
    void save(User u);
    String encryptPassword(String password) throws NoSuchAlgorithmException;
    User registerUser(String password, String email, String role, String name, String moderateCategory) throws NoSuchAlgorithmException;
    User login(String email, String password) throws NoSuchAlgorithmException;
}
