package com.esliceu.backend.services;

import com.esliceu.backend.entities.User;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;

public interface UserService {
    User findUserByEmailEquals(String email);
    User findById(Long user_id);
    void save(User u);
    String encryptPassword(String password) throws NoSuchAlgorithmException;
    User registerUser(String password, String email, String role, String name, String moderateCategory) throws NoSuchAlgorithmException;
    User login(String email, String password) throws NoSuchAlgorithmException;
    HashMap<String,Object> updateUserToken(String actual, String email, String name, String avatar, String password, String newPass) throws NoSuchAlgorithmException;
}
