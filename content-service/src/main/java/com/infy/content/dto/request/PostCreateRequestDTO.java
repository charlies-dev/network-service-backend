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

    @NotNull(message = "{post.userId.notnull}")
    private Long userId;

    @NotBlank(message = "{post.content.notblank}")
    private String content;

    @NotNull(message = "{post.type.notnull}")
    private PostType type;

    @NotNull(message = "{post.status.notnull}")
    private PostStatus status;

    private LocalDateTime scheduledAt;

    private Set<String> hashtags;

    private List<Long> mentionedUserIds;
}
