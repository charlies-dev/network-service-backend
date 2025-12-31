package com.infy.recruitment.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.infy.recruitment.entity.Job;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> ,JpaSpecificationExecutor<Job>{
     List<Job> findByCompanyId(Long companyId);

    List<Job> findByIdIn(List<Long> ids);

    List<Job> findByApplicationDeadlineGreaterThanEqual(LocalDate currentDate);
}
