package com.infy.network.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.infy.network.entity.Connection;
import com.infy.network.enums.ConnectionStatus;

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

    @Query("""
        SELECT COUNT(c) FROM Connection c
        WHERE (c.userId = :userId OR c.connectedUserId = :userId)
          AND c.status = 'ACCEPTED'
    """)
    Long countAcceptedConnections(@Param("userId") Long userId);

    @Query("""
        SELECT COUNT(c) FROM Connection c
        WHERE c.connectedUserId = :userId
          AND c.status = 'PENDING'
    """)
    Long countPendingConnections(@Param("userId") Long userId);

    @Query("""
        SELECT c FROM Connection c
        WHERE ((c.userId = :userIdA AND c.connectedUserId = :userIdB)
           OR (c.userId = :userIdB AND c.connectedUserId = :userIdA))
          AND c.status = 'ACCEPTED'
    """)
    Optional<Connection> findAcceptedConnectionBetween(
            @Param("userIdA") Long userIdA,
            @Param("userIdB") Long userIdB
    );

    @Query("""
        SELECT c FROM Connection c
        WHERE c.userId = :userId
          AND c.connectedUserId = :targetUserId
          AND c.status = 'PENDING'
    """)
    Optional<Connection> findPendingRequestFrom(
            @Param("userId") Long userId,
            @Param("targetUserId") Long targetUserId
    );

    @Query("""
        SELECT c FROM Connection c
        WHERE c.connectedUserId = :userId
          AND c.userId = :targetUserId
          AND c.status = 'PENDING'
    """)
    Optional<Connection> findPendingRequestTo(
            @Param("userId") Long userId,
            @Param("targetUserId") Long targetUserId
    );
}
