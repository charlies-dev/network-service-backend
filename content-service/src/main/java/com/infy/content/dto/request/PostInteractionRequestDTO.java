package com.infy.content.dto.request;

import com.infy.content.enums.InteractionType;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PostInteractionRequestDTO {

    @NotNull
    private Long userId;

    @NotNull
    private InteractionType type;

    private String commentText;
}
