package com.infy.user.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.infy.user.dto.request.UpdateUserRequestDTO;
import com.infy.user.dto.request.UserDetailsRequestDTO;
import com.infy.user.dto.response.UserResponseDTO;
import com.infy.user.service.UserService;

import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@Validated
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PutMapping("/{userId}")
    public ResponseEntity<Void> updateUser(
            @PathVariable Long userId,
            @Valid @RequestBody UpdateUserRequestDTO requestDTO) {
        userService.updateUser(userId, requestDTO);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{userId}/verify")
    public ResponseEntity<Void> updateVerifyUserStatus(
            @PathVariable Long userId,
            @RequestParam Boolean isVerified) {
        userService.updateVerifyUserStatus(userId, isVerified);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{userId}/forgot-password")
    public ResponseEntity<Void> forgotPassword(
            @PathVariable Long userId,
            @RequestParam String newPassword) {
        userService.forgotPassword(userId, newPassword);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{userId}")
    @PermitAll
    public ResponseEntity<UserResponseDTO> getUserById(
            @PathVariable Long userId) {
        UserResponseDTO response = userService.getUserById(userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @PermitAll
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<UserResponseDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/search")
    public ResponseEntity<List<UserResponseDTO>> searchUsersByName(
            @RequestParam String name) {
        List<UserResponseDTO> users = userService.searchUsersByName(name);
        return ResponseEntity.ok(users);
    }
    @PostMapping("/ids")
    public ResponseEntity<List<UserResponseDTO>> getUsersByIds(
            @RequestBody List<Long> userIds) {
        List<UserResponseDTO> users = userService.getUserByIds(userIds);
        return ResponseEntity.ok(users);
    }

    @PostMapping("/{userId}/details")
    public ResponseEntity<String> addUserDetails(
            @PathVariable Long userId,
            @Valid @RequestBody UserDetailsRequestDTO requestDTO) {
        String response = userService.addUserDetails(userId, requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
