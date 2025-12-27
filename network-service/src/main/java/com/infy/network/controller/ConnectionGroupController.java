package com.infy.network.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.infy.network.dto.request.AddGroupMembersRequestDTO;
import com.infy.network.dto.request.ConnectionGroupRequestDTO;
import com.infy.network.dto.response.ConnectionGroupResponseDTO;
import com.infy.network.dto.response.UserDetailsDTO;
import com.infy.network.service.ConnectionGroupService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/connection-groups")
@RequiredArgsConstructor
public class ConnectionGroupController {

    private final ConnectionGroupService connectionGroupService;

    /* ================= CREATE GROUP ================= */

    @PostMapping
    public ResponseEntity<ConnectionGroupResponseDTO> createGroup(
            @Valid @RequestBody ConnectionGroupRequestDTO requestDTO) {

        return ResponseEntity.ok(
                connectionGroupService.createNewGroup(requestDTO)
        );
    }

    /* ================= UPDATE GROUP ================= */

    @PutMapping("/{groupId}")
    public ResponseEntity<ConnectionGroupResponseDTO> updateGroup(
            @PathVariable Long groupId,
            @RequestParam String name) {

        return ResponseEntity.ok(
                connectionGroupService.updateGroupDetails(groupId, name)
        );
    }

    /* ================= ADD MEMBERS ================= */

    @PostMapping("/members")
    public ResponseEntity<Void> addGroupMembers(
            @Valid @RequestBody AddGroupMembersRequestDTO requestDTO) {

        connectionGroupService.addGroupsMember(requestDTO);
        return ResponseEntity.noContent().build();
    }

    /* ================= GET GROUPS BY USER ================= */

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ConnectionGroupResponseDTO>> getGroupsByUser(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                connectionGroupService.getGroupsByUserId(userId)
        );
    }

    /* ================= GET GROUP MEMBERS ================= */

    @GetMapping("/{groupId}/members")
    public ResponseEntity<List<UserDetailsDTO>> getGroupMembers(
            @PathVariable Long groupId) {

        return ResponseEntity.ok(
                connectionGroupService.getGroupsMembersByGroupId(groupId)
        );
    }

    /* ================= REMOVE GROUP MEMBERS ================= */

    @DeleteMapping("/{groupId}/members")
    public ResponseEntity<Void> removeGroupMembers(
            @PathVariable Long groupId,
            @RequestBody List<Long> userIds) {

        connectionGroupService.removeGroupMembers(groupId, userIds);
        return ResponseEntity.noContent().build();
    }

    /* ================= DELETE GROUP ================= */

    @DeleteMapping("/{groupId}")
    public ResponseEntity<Void> deleteGroup(
            @PathVariable Long groupId,
            @RequestParam Long userId) {

        connectionGroupService.deleteGroup(groupId, userId);
        return ResponseEntity.noContent().build();
    }
}

