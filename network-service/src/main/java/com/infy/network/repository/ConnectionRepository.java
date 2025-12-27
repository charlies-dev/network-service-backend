package com.infy.network.repository;

import com.infy.network.entity.Connection;
import com.infy.network.enums.ConnectionStatus;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ConnectionRepository extends JpaRepository<Connection, Long> {


    Optional<Connection> findByUserIdAndConnectedUserId(
            Long userId,
            Long connectedUserId
    );

    List<Connection> findByConnectedUserIdAndStatus(
            Long connectedUserId,
            ConnectionStatus status
    );

    @Query("""
        SELECT c FROM Connection c
        WHERE (c.userId = :userId OR c.connectedUserId = :userId)
          AND c.status = 'ACCEPTED'
    """)
    List<Connection> findAcceptedConnections(@Param("userId") Long userId);


}
