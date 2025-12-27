package com.infy.user.repository;

import com.infy.user.entity.UserSkill;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


public interface UserSkillRepository extends JpaRepository<UserSkill, Long> {

    List<UserSkill> findByUserId(Long userId);

    boolean existsByUserIdAndSkillId(Long userId, Long skillId);
     List<UserSkill> findBySkillId(Long skillId);
}
