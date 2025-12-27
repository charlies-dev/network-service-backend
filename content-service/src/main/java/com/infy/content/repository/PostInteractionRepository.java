package com.infy.content.repository;

import com.infy.content.entity.PostInteraction;
import com.infy.content.enums.InteractionType;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostInteractionRepository extends JpaRepository<PostInteraction, Long> {

    List<PostInteraction> findByPostId(Long postId, Sort sort);

    long countByPostIdAndType(Long postId, InteractionType type);

     List<PostInteraction> findByPostIdAndType(Long postId, InteractionType type,Sort sort);
}
