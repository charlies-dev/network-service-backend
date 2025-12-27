package com.infy.content.dto.request;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import com.infy.content.enums.PostStatus;
import com.infy.content.enums.PostType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PostCreateRequestDTO {

    @NotNull
    private Long userId;

    @NotBlank
    private String content;

    @NotNull
    private PostType type;

    @NotNull
    private PostStatus status;

    private LocalDateTime scheduledAt;

    private Set<String> hashtags;

    private List<Long> mentionedUserIds;
}
