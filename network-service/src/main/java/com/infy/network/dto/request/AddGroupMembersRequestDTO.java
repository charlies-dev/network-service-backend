 

 package com.infy.network.dto.request;
 
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class AddGroupMembersRequestDTO {

    @NotNull
    private Long groupId;

    @NotEmpty
    private List<Long> userIds;
}