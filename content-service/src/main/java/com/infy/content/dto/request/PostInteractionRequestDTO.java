package com.infy.content.dto.request;

import com.infy.content.enums.InteractionType;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PostInteractionRequestDTO {

    @NotNull(message = "{post.interaction.userId.notnull}")
    private Long userId;

    @NotNull(message = "{post.interaction.interactionType.notnull}")
    private InteractionType type;

    private String commentText;
}
