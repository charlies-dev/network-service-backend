package com.infy.user.dto.request;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UserSkillListRequestDTO {

    @NotEmpty
    @Valid
    private List<UserSkillRequestDTO> skills;
}
