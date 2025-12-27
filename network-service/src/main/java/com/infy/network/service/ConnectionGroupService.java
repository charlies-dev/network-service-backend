package com.infy.network.service;

import java.util.List;

import com.infy.network.dto.request.AddGroupMembersRequestDTO;
import com.infy.network.dto.request.ConnectionGroupRequestDTO;
import com.infy.network.dto.response.ConnectionGroupResponseDTO;
import com.infy.network.dto.response.UserDetailsDTO;

public interface ConnectionGroupService {

    ConnectionGroupResponseDTO createNewGroup(ConnectionGroupRequestDTO requestDTO);

    ConnectionGroupResponseDTO updateGroupDetails(Long groupId, String name);

    void addGroupsMember(AddGroupMembersRequestDTO requestDTO);

    List<ConnectionGroupResponseDTO> getGroupsByUserId(Long userId);

    List<UserDetailsDTO> getGroupsMembersByGroupId(Long groupId);

    void removeGroupMembers(Long groupId, List<Long> userIds);
    void deleteGroup(Long groupId, Long userId);
}
