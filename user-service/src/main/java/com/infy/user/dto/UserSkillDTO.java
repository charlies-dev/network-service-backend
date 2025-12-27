package com.infy.user.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserSkillDTO {
     @NotNull
    private String skillName;
}
