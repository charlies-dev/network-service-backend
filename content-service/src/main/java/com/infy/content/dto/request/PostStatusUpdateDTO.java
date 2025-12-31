package com.infy.content.dto.request;

import java.time.LocalDateTime;

import com.infy.content.enums.PostStatus;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PostStatusUpdateDTO {

    @NotNull(message = "{post.status.update.status.notnull}")
    private PostStatus status;

    private LocalDateTime scheduledAt;
}
