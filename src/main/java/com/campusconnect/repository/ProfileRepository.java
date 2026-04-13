package com.campusconnect.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.campusconnect.model.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
}