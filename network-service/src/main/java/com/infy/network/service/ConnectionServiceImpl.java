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
import com.infy.network.dto.response.ConnectionCountDTO;
import com.infy.network.dto.response.ConnectionResponseDTO;
import com.infy.network.dto.response.ConnectionStatusDTO;
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
       @Override
    @Transactional
    public Long sendConnectionRequest(ConnectionRequestDTO dto) {
        
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
        connectionRepository
                .findByUserIdAndConnectedUserId(
                        dto.getConnectedUserId(),
                        dto.getUserId())
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

    @Override
    public List<ConnectionResponseDTO> getConnectionByUserId(Long userId) {

        return connectionRepository
                .findAcceptedConnections(userId)
                .stream()
                
                .map(c -> {
                    ConnectionResponseDTO connectionResponseDTO = modelMapper.map(c, ConnectionResponseDTO.class);
                    
                    Long otherUserId = c.getUserId().equals(userId) 
                            ? c.getConnectedUserId() 
                            : c.getUserId();
                    
                    UserDetailsDTO user = userClient.getUserDetailsById(otherUserId);
                    UserDetailsDTO connectedUser = userClient.getUserDetailsById(userId);
                    
                    connectionResponseDTO.setUser(user);
                    connectionResponseDTO.setConnectedUser(connectedUser);
                    return connectionResponseDTO;
                })
                .toList();
    }

    @Override
    @Transactional
    public void removeConnection(Long connectionId) {

        Connection connection = connectionRepository.findById(connectionId)
                .orElseThrow(() -> new InfyLinkedInException("Connection not found"));

        connectionRepository.delete(connection);
    }

    @Override
    public ConnectionCountDTO getConnectionCount(Long userId) {
        userClient.validateUserExists(userId);
        
        Long acceptedConnectionCount = connectionRepository.countAcceptedConnections(userId);
        Long pendingRequestCount = connectionRepository.countPendingConnections(userId);

        return new ConnectionCountDTO(
                userId,
                acceptedConnectionCount != null ? acceptedConnectionCount : 0L,
                pendingRequestCount != null ? pendingRequestCount : 0L
        );
    }

    @Override
    public ConnectionStatusDTO getConnectionStatus(Long userId, Long targetUserId) {
        
        userClient.validateUserExists(userId);
        userClient.validateUserExists(targetUserId);

        boolean connected = false;
        boolean requestPending = false;
        String connectionMessage = "No connection";

        Optional<Connection> acceptedConnection = connectionRepository.findAcceptedConnectionBetween(userId, targetUserId);
        if (acceptedConnection.isPresent()) {
            connected = true;
            connectionMessage = "Connected";
        } else {
            
            Optional<Connection> pendingFromUser = connectionRepository.findPendingRequestFrom(userId, targetUserId);
            if (pendingFromUser.isPresent()) {
                requestPending = true;
                connectionMessage = "Request pending";
            } else {
             Optional<Connection> pendingToUser = connectionRepository.findPendingRequestTo(userId, targetUserId);
                if (pendingToUser.isPresent()) {
                    requestPending = true;
                    connectionMessage = "Request pending from user";
                }
            }
        }

        return ConnectionStatusDTO.builder()
                .userId(userId)
                .targetUserId(targetUserId)
                .connected(connected)
                .requestPending(requestPending)
                .connectionMessage(connectionMessage)
                .build();
    }
}
