package com.campusconnect.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.campusconnect.model.Job;

public interface JobRepository extends JpaRepository<Job, Long> {
}