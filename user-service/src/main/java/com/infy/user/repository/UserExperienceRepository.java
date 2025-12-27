package com.infy.user.repository;

import com.infy.user.entity.UserExperience;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserExperienceRepository extends JpaRepository<UserExperience, Long> {
    List<UserExperience> findByUserId(Long userId);
}
