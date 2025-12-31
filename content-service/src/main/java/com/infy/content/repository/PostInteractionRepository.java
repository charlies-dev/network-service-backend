package com.infy.content.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.infy.content.entity.PostInteraction;
import com.infy.content.enums.InteractionType;

@Repository
public interface PostInteractionRepository extends JpaRepository<PostInteraction, Long> {

    List<PostInteraction> findByPostId(Long postId, Sort sort);

    long countByPostIdAndType(Long postId, InteractionType type);

    List<PostInteraction> findByPostIdAndType(Long postId, InteractionType type, Sort sort);

    Optional<PostInteraction> findByPostIdAndUserIdAndType(Long postId, Long userId, InteractionType type);
}
