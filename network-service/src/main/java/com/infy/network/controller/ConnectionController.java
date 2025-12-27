package com.infy.network.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infy.network.dto.request.ConnectionRequestDTO;
import com.infy.network.dto.request.ConnectionStatusUpdateDTO;
import com.infy.network.dto.response.ConnectionResponseDTO;
import com.infy.network.service.ConnectionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/connections")
@RequiredArgsConstructor
public class ConnectionController {

    private final ConnectionService connectionService;

    @PostMapping
    public ResponseEntity<Long> sendRequest(
            @Valid @RequestBody ConnectionRequestDTO dto) {

        return ResponseEntity.ok(
                connectionService.sendConnectionRequest(dto)
        );
    }

    @PatchMapping("/{connectionId}/status")
    public ResponseEntity<Void> updateStatus(
            @PathVariable Long connectionId,
            @Valid @RequestBody ConnectionStatusUpdateDTO dto) {

        connectionService.updateConnectionRequestStatus(connectionId, dto);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/pending/{userId}")
    public ResponseEntity<List<ConnectionResponseDTO>> getPending(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                connectionService.getPendingConnectionByUserId(userId)
        );
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ConnectionResponseDTO>> getConnections(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                connectionService.getConnectionByUserId(userId)
        );
    }

    @DeleteMapping("/{connectionId}")
    public ResponseEntity<Void> delete(
            @PathVariable Long connectionId) {

        connectionService.removeConnection(connectionId);
        return ResponseEntity.noContent().build();
    }
}

