package com.campusconnect.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.campusconnect.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
}