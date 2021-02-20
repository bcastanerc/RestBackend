package com.esliceu.backend.services;

import com.esliceu.backend.entities.User;
import com.esliceu.backend.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;

    @Override
    public User findById(Long user_id) {
        return userRepository.findById(user_id).get();
    }

    @Override
    public void save(User u) {
        userRepository.save(u);
    }
}
