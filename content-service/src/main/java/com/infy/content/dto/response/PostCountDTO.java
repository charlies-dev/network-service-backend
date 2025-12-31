package com.infy.content.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostCountDTO {
    private Long userId;
    private Long postCount;
    private Long publishedPostCount;
    private Long draftPostCount;
}
