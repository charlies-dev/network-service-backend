package com.infy.content.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostInteractionMatrixDTO {

    private Long likeCount;
    private Long commentCount;
}
