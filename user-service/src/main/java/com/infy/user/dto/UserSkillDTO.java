package com.infy.user.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserSkillDTO {

    Long id;
     @NotNull
    private String skillName;
}
