package com.infy.content.dto.response;

import java.time.LocalDateTime;

import com.infy.content.enums.InteractionType;

import lombok.Data;

@Data
public class PostInteractionResponseDTO {

    private Long id;
    private Long postId;
    private UserDetailsDTO user;
    private InteractionType type;
    private String commentText;
    private LocalDateTime createdAt;
}
