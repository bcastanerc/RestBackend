package com.esliceu.backend.services;

import com.esliceu.backend.entities.User;

public interface UserService {
    User findById(Long user_id);
    void save(User u);
}
