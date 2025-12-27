package com.infy.network.repository;

import com.infy.network.entity.ConnectionGroup;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConnectionGroupRepository extends JpaRepository<ConnectionGroup, Long> {

    Optional<ConnectionGroup> findByUserIdAndName(Long userId, String name);

    List<ConnectionGroup> findByUserId(Long userId);
}
