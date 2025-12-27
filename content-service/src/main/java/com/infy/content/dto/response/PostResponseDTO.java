package com.infy.content.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import com.infy.content.enums.PostStatus;
import com.infy.content.enums.PostType;

import lombok.Data;

@Data
public class PostResponseDTO {

    private Long id;
    private UserResponseDTO user;
    private String content;
    private PostType type;
    private PostStatus status;
    private LocalDateTime scheduledAt;
    private Set<String> hashtags;
    private List<UserResponseDTO> mentionedUser;
}
