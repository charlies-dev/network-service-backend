package com.infy.content.service;

import java.util.List;

import com.infy.content.dto.request.PostCreateRequestDTO;
import com.infy.content.dto.request.PostStatusUpdateDTO;
import com.infy.content.dto.request.PostUpdateRequestDTO;
import com.infy.content.dto.response.PostCountDTO;
import com.infy.content.dto.response.PostResponseDTO;
import com.infy.content.dto.response.UserStatsDTO;

public interface PostService {

  Long createPost(PostCreateRequestDTO requestDTO);

    void updatePost(Long postId, PostUpdateRequestDTO requestDTO);

    void updatePostStatus(Long postId, PostStatusUpdateDTO requestDTO);

    List<PostResponseDTO> getAllPosts();

    PostResponseDTO getPostByPostId(Long postId);

    List<PostResponseDTO> getPostByUserId(Long userId);

    List<PostResponseDTO> getPostByMentionedUserId(Long userId);

    void deletePostById(Long postId);

    PostCountDTO getPostCount(Long userId);

    UserStatsDTO getUserStats(Long userId);

    List<PostResponseDTO> getConnectedUsersPosts(Long userId);
}
