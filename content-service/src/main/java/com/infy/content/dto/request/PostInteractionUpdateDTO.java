package com.infy.content.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PostInteractionUpdateDTO {

    @NotBlank
    private String commentText;
}
