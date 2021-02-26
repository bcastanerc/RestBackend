package com.esliceu.backend.services;

import com.esliceu.backend.entities.Category;
import com.esliceu.backend.entities.User;
import com.esliceu.backend.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;

    @Autowired
    CategoryService categoryService;

    @Autowired
    TokenService tokenService;

    @Override
    public User findUserByEmailEquals(String email) {
        return userRepository.findUserByEmailEquals(email);
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
        if (role.equals("moderator")){
            u.setCategoryModerated(categoryService.findCategoryBySlug(moderateCategory));
        }
        u.setPassword(encryptPassword(password));
        return u;
    }

    @Override
    public User login(String email, String password) throws NoSuchAlgorithmException {
        User u = findUserByEmailEquals(email);
        if ( u !=null && encryptPassword(password).equals(u.getPassword())) return u;
        return null;
    }

    @Override
    public HashMap<String, Object> updateUserToken(String actual, String email, String name, String avatar, String password, String newPass) throws NoSuchAlgorithmException {
        User u = findUserByEmailEquals(actual);
        if ((password != null && newPass !=null)|| (email != null && name != null)){
            HashMap<String, Object> tokenUser = new HashMap<>();
            if (password != null && u.getPassword().equals(encryptPassword(password)) && !encryptPassword(newPass).equals(u.getPassword())){
                u.setPassword(encryptPassword(newPass));
                save(u);
                tokenUser.put("token", tokenService.newToken(email));
                tokenUser.put("user", u);
                return  tokenUser;
            }
            if (findUserByEmailEquals(email) == null || email.equals(actual)) {
                u.setEmail(email);
                u.setAvatarUrl(avatar);
                u.setName(name);
                save(u);
                tokenUser.put("token", tokenService.newToken(email));
                tokenUser.put("user", u);
                return  tokenUser;
            }
        }
        return null;
    }
}
