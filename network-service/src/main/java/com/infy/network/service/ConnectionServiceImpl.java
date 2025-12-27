package com.infy.network.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.infy.network.client.ContentClient;
import com.infy.network.client.UserClient;
import com.infy.network.dto.request.ConnectionRequestDTO;
import com.infy.network.dto.request.ConnectionStatusUpdateDTO;
import com.infy.network.dto.response.ConnectionResponseDTO;
import com.infy.network.dto.response.UserDetailsDTO;
import com.infy.network.entity.Connection;
import com.infy.network.enums.ConnectionStatus;
import com.infy.network.enums.NotificationType;
import com.infy.network.exception.InfyLinkedInException;
import com.infy.network.repository.ConnectionRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConnectionServiceImpl implements ConnectionService {

    private final ConnectionRepository connectionRepository;
    private final UserClient userClient;
    private final ContentClient contentClient;
    private final ModelMapper modelMapper;

    /* ================= SEND REQUEST ================= */

    @Override
    @Transactional
    public Long sendConnectionRequest(ConnectionRequestDTO dto) {
        long userA = Math.min(dto.getUserId(), dto.getConnectedUserId());
        long userB = Math.max(dto.getUserId(), dto.getConnectedUserId());

        Optional<Connection> existing = connectionRepository.findByUserIdAndConnectedUserId(userA, userB);

        if (existing.isPresent()) {

            throw new InfyLinkedInException("Connection already exists");
        }
        userClient.validateUserExists(dto.getUserId());
        userClient.validateUserExists(dto.getConnectedUserId());

        connectionRepository
                .findByUserIdAndConnectedUserId(
                        dto.getUserId(),
                        dto.getConnectedUserId())
                .ifPresent(c -> {
                    throw new InfyLinkedInException(
                            "Connection request already exists");
                });

        Connection connection = Connection.builder()
                .userId(dto.getUserId())
                .connectedUserId(dto.getConnectedUserId())
                .status(ConnectionStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .build();

        Connection saved = connectionRepository.save(connection);

        contentClient.sendNotification(
                dto.getConnectedUserId(),
                "You have a new connection request",
                NotificationType.CONNECTION);

        return saved.getId();
    }

    /* ================= UPDATE STATUS ================= */

    @Override
    @Transactional
    public void updateConnectionRequestStatus(
            Long connectionId,
            ConnectionStatusUpdateDTO dto) {

        Connection connection = connectionRepository.findById(connectionId)
                .orElseThrow(() -> new InfyLinkedInException("Connection not found"));

        connection.setStatus(dto.getStatus());
        connection.setUpdatedAt(LocalDateTime.now());

        contentClient.sendNotification(
                connection.getUserId(),
                "Your connection request was " +
                        dto.getStatus().name().toLowerCase(),
                NotificationType.CONNECTION);
    }

    /* ================= PENDING REQUESTS ================= */

    @Override
    public List<ConnectionResponseDTO> getPendingConnectionByUserId(
            Long userId) {

        return connectionRepository
                .findByConnectedUserIdAndStatus(
                        userId,
                        ConnectionStatus.PENDING)
                .stream()
                .map(c -> {
                    ConnectionResponseDTO connectionResponseDTO = modelMapper.map(c, ConnectionResponseDTO.class);
                    UserDetailsDTO user = userClient.getUserDetailsById(c.getUserId());
                    UserDetailsDTO connectedUser = userClient.getUserDetailsById(c.getConnectedUserId());

                    connectionResponseDTO.setConnectedUser(connectedUser);
                    connectionResponseDTO.setUser(user);
                    return connectionResponseDTO;

                })
                .toList();
    }

    /* ================= ACCEPTED CONNECTIONS ================= */

    @Override
    public List<ConnectionResponseDTO> getConnectionByUserId(Long userId) {

        return connectionRepository
                .findAcceptedConnections(userId)
                .stream()
                .map(c -> {
                    ConnectionResponseDTO connectionResponseDTO = modelMapper.map(c, ConnectionResponseDTO.class);
                    UserDetailsDTO user = userClient.getUserDetailsById(c.getUserId());
                    UserDetailsDTO connectedUser = userClient.getUserDetailsById(c.getConnectedUserId());

                    connectionResponseDTO.setConnectedUser(connectedUser);
                    connectionResponseDTO.setUser(user);
                    return connectionResponseDTO;
                })
                .toList();
    }

    /* ================= REMOVE ================= */

    @Override
    @Transactional
    public void removeConnection(Long connectionId) {

        Connection connection = connectionRepository.findById(connectionId)
                .orElseThrow(() -> new InfyLinkedInException("Connection not found"));

        connectionRepository.delete(connection);
    }
}
