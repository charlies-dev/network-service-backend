package com.infy.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.infy.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

     Optional<User> findByEmailId(String emailId);

    boolean existsByEmailId(String emailId);

    boolean existsByMobileNo(String mobileNo);

    List<User> findByFirstNameIgnoreCaseContaining(String firstName);

    List<User> findByLastNameIgnoreCaseContaining(String lastName);

    @Query("""
        SELECT u FROM User u
        WHERE LOWER(u.firstName) LIKE LOWER(CONCAT('%', :name, '%'))
           OR LOWER(u.lastName) LIKE LOWER(CONCAT('%', :name, '%')) OR LOWER(CONCAT(u.firstName, ' ', u.lastName)) LIKE LOWER(CONCAT('%', :name, '%'))
    """)
    List<User> searchByName(@Param("name") String name);
}
