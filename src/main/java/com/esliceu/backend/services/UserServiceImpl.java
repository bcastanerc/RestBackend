package com.esliceu.backend.services;

import com.esliceu.backend.entities.User;
import com.esliceu.backend.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;

    @Override
    public User findByEmailEquals(String email) {
        return userRepository.findByEmailEquals(email);
    }

    @Override
    public User findById(Long user_id) {
        return userRepository.findById(user_id).get();
    }

    @Override
    public void save(User u) {
        userRepository.save(u);
    }

    /**
     * Encrypt the password to sha-512.
     * @param password The usser password.
     * @return returns the encripted password 64chars.
     * @throws NoSuchAlgorithmException exception thrown by the library.
     */
    public String encryptPassword(String password) throws NoSuchAlgorithmException {

        MessageDigest md = MessageDigest.getInstance("SHA-512");
        md.update(password.getBytes());
        byte[] digest = md.digest();

        StringBuilder hexString = new StringBuilder();
        for (byte b : digest) {
            hexString.append(Integer.toHexString(0xFF & b));
        }
        return hexString.toString();
    }

    @Override
    public User registerUser(String password, String email, String role, String name, String moderateCategory) throws NoSuchAlgorithmException {
        User u = new User();
        u.setRole(role);
        u.setName(name);
        u.setEmail(email);
        u.setPassword(encryptPassword(password));
        return u;
    }

    @Override
    public User login(String email, String password) throws NoSuchAlgorithmException {
        User u = findByEmailEquals(email);
        if (encryptPassword(password).equals(u.getPassword())) return u;
        return null;
    }
}
