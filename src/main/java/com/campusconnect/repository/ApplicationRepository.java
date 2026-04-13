package com.campusconnect.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.campusconnect.model.Application;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
}