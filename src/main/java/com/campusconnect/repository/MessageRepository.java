package com.campusconnect.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.campusconnect.model.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {
}