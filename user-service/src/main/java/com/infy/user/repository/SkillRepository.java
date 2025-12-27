package com.infy.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.infy.user.entity.Skill;



public interface SkillRepository extends JpaRepository<Skill, Long> {
    Optional<Skill>  findByName(String name);
     Optional<Skill> findByNameIgnoreCase(String name);
}
