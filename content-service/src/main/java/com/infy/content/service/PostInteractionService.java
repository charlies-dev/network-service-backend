package com.infy.content.service;

import java.util.List;

import com.infy.content.dto.request.PostInteractionRequestDTO;
import com.infy.content.dto.request.PostInteractionUpdateDTO;
import com.infy.content.dto.response.PostInteractionMatrixDTO;
import com.infy.content.dto.response.PostInteractionResponseDTO;
import com.infy.content.dto.response.UserDetailsDTO;
import com.infy.content.enums.InteractionType;

public interface PostInteractionService {

  Long addPostInteraction(Long postId, PostInteractionRequestDTO requestDTO);

    void updatePostInteraction(Long interactionId, PostInteractionUpdateDTO requestDTO);

    void removePostInteraction(Long interactionId);

    List<PostInteractionResponseDTO> getPostInteractionByPostId(Long postId);

    PostInteractionMatrixDTO getPostInteractionMatrixByPostId(Long postId);
    List<UserDetailsDTO> getUsersByInteractionType(
            Long postId,
            InteractionType interactionType
    );
}
