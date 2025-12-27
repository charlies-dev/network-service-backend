package com.infy.content.service;

import com.infy.content.dto.request.PostCreateRequestDTO;
import com.infy.content.dto.request.PostStatusUpdateDTO;
import com.infy.content.dto.request.PostUpdateRequestDTO;
import com.infy.content.dto.response.PostResponseDTO;
import java.util.List;

public interface PostService {

  Long createPost(PostCreateRequestDTO requestDTO);

    void updatePost(Long postId, PostUpdateRequestDTO requestDTO);

    void updatePostStatus(Long postId, PostStatusUpdateDTO requestDTO);

    List<PostResponseDTO> getAllPosts();

    PostResponseDTO getPostByPostId(Long postId);

    List<PostResponseDTO> getPostByUserId(Long userId);

    List<PostResponseDTO> getPostByMentionedUserId(Long userId);

    void deletePostById(Long postId);
}
