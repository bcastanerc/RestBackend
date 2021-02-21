package com.esliceu.backend.repos;

import com.esliceu.backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByEmailEquals(String email);
}
