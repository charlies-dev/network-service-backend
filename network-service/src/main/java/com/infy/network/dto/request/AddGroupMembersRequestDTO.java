package com.infy.network.dto.request;
 
import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddGroupMembersRequestDTO {

    @NotNull
    private Long groupId;

    @NotEmpty(message = "{connection.group.members.notnull}")
    private List<Long> userIds;
}