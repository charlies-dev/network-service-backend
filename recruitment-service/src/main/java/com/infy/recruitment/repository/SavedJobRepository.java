package com.infy.recruitment.repository;

import com.infy.recruitment.entity.SavedJob;
import com.infy.recruitment.entity.SavedJobId;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SavedJobRepository extends JpaRepository<SavedJob, SavedJobId> {
     List<SavedJob> findByUserIdOrderBySavedAtDesc(Long userId);

    boolean existsByUserIdAndJobId(Long userId, Long jobId);
}
