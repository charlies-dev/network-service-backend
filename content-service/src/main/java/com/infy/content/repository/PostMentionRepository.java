package com.infy.content.repository;

import com.infy.content.entity.PostMention;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostMentionRepository extends JpaRepository<PostMention, Long> {
}
