package com.infy.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class UserSkillRequestDTO {
      @NotBlank
    private String skillName;
}
