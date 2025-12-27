package com.infy.content.dto.request;

import java.util.List;
import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PostUpdateRequestDTO {

    @NotBlank
    private String content;

    private Set<String> hashtags;

    private List<Long> mentionedUserIds;
}
