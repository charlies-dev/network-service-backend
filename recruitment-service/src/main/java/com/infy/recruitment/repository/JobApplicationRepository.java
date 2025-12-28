package com.infy.recruitment.repository;

import com.infy.recruitment.entity.JobApplication;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {

     List<JobApplication> findByUserIdOrderByAppliedAtDesc(Long userId);

    List<JobApplication> findByJobIdOrderByAppliedAtDesc(Long jobId);

    boolean existsByJobIdAndUserId(Long jobId, Long userId);
}
