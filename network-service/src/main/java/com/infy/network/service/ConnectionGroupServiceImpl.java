package com.infy.network.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.infy.network.client.UserClient;
import com.infy.network.dto.request.AddGroupMembersRequestDTO;
import com.infy.network.dto.request.ConnectionGroupRequestDTO;
import com.infy.network.dto.response.ConnectionGroupResponseDTO;
import com.infy.network.dto.response.UserDetailsDTO;
import com.infy.network.entity.ConnectionGroup;
import com.infy.network.entity.ConnectionGroupMember;
import com.infy.network.exception.InfyLinkedInException;
import com.infy.network.repository.ConnectionGroupMemberRepository;
import com.infy.network.repository.ConnectionGroupRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConnectionGroupServiceImpl implements ConnectionGroupService {

        private final ConnectionGroupRepository groupRepository;
        private final ConnectionGroupMemberRepository memberRepository;
        private final UserClient userClient;
        private final ModelMapper modelMapper;

        @Override
        @Transactional
        public ConnectionGroupResponseDTO createNewGroup(ConnectionGroupRequestDTO requestDTO) {

                userClient.validateUserExists(requestDTO.getUserId());

                groupRepository.findByUserIdAndName(requestDTO.getUserId(), requestDTO.getName())
                                .ifPresent(g -> {
                                        throw new InfyLinkedInException("Group name already exists for this user");
                                });

                ConnectionGroup group = ConnectionGroup.builder()
                                .userId(requestDTO.getUserId())
                                .name(requestDTO.getName())
                                .addedAt(LocalDateTime.now())
                                .build();

                return modelMapper.map(groupRepository.save(group),
                                ConnectionGroupResponseDTO.class);
        }

        @Override
        @Transactional
        public ConnectionGroupResponseDTO updateGroupDetails(Long groupId, String name) {

                ConnectionGroup group = groupRepository.findById(groupId)
                                .orElseThrow(() -> new InfyLinkedInException("Connection group not found"));

                group.setName(name);

                return modelMapper.map(groupRepository.save(group),
                                ConnectionGroupResponseDTO.class);
        }

        @Override
        @Transactional
        public void addGroupsMember(AddGroupMembersRequestDTO requestDTO) {

                ConnectionGroup group = groupRepository.findById(requestDTO.getGroupId())
                                .orElseThrow(() -> new InfyLinkedInException("Connection group not found"));

                if (requestDTO.getUserIds().contains(group.getUserId())) {
                        throw new InfyLinkedInException("Group creator cannot be added as member");
                }

                userClient.getUsersByIds(requestDTO.getUserIds());

                Set<ConnectionGroupMember> members = requestDTO.getUserIds()
                                .stream()
                                .map(userId -> ConnectionGroupMember.builder()
                                                .groupId(group.getId())
                                                .connectedUserId(userId)
                                                .addedAt(LocalDateTime.now())
                                                .build())
                                .collect(Collectors.toSet());

                memberRepository.saveAll(members);
        }

        @Override
        public List<ConnectionGroupResponseDTO> getGroupsByUserId(Long userId) {

                userClient.validateUserExists(userId);

                return groupRepository.findByUserId(userId)
                                .stream()
                                .map(group -> modelMapper.map(group, ConnectionGroupResponseDTO.class))
                                .toList();
        }

        @Override
        public List<UserDetailsDTO> getGroupsMembersByGroupId(Long groupId) {

                groupRepository.findById(groupId)
                                .orElseThrow(() -> new InfyLinkedInException("Connection group not found"));

                List<Long> user_ids = memberRepository.findByGroupId(groupId)
                                .stream()
                                .map(ConnectionGroupMember::getConnectedUserId)

                                .toList();

                List<UserDetailsDTO> list = userClient.getUsersByIds(user_ids).stream().map(userEntity -> {
                        UserDetailsDTO user = new UserDetailsDTO();

                        user.setId(userEntity.getId());
                        user.setFirstName(userEntity.getFirstName());
                        user.setLastName(userEntity.getLastName());
                        user.setEmailId(userEntity.getEmailId());
                        if (userEntity.getProfile()!=null) {
                                
                                user.setProfilePhotoUrl(userEntity.getProfile().getProfilePhotoUrl());
                        }
                        return user;
                }).toList();
                return list;
        }

        @Override
        @Transactional
        public void removeGroupMembers(Long groupId, List<Long> userIds) {

                groupRepository.findById(groupId)
                                .orElseThrow(() -> new InfyLinkedInException("Connection group not found"));

                memberRepository.deleteByGroupIdAndConnectedUserIdIn(groupId, userIds);
        }

        @Override
        @Transactional
        public void deleteGroup(Long groupId, Long userId) {

                ConnectionGroup group = groupRepository.findById(groupId)
                                .orElseThrow(() -> new InfyLinkedInException("Connection group not found"));

                if (!group.getUserId().equals(userId)) {
                        throw new InfyLinkedInException("You are not authorized to delete this group");
                }

                groupRepository.delete(group);
        }
}
