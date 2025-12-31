package com.infy.content.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.infy.content.entity.Post;
import com.infy.content.enums.PostStatus;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByUserId(Long userId);

    @Query("""
       SELECT DISTINCT p FROM Post p
       JOIN p.mentions m
       WHERE m.mentionedUserId = :userId
    """)
    List<Post> findByMentionedUserId(Long userId);

    List<Post> findByStatus(PostStatus status, Sort sort);

    Long countByUserId(Long userId);

    Long countByUserIdAndStatus(Long userId, PostStatus status);

    List<Post> findByUserIdAndStatus(Long userId, PostStatus status);
}
