package com.infy.network.service;

import java.util.List;

import com.infy.network.dto.request.ConnectionRequestDTO;
import com.infy.network.dto.request.ConnectionStatusUpdateDTO;
import com.infy.network.dto.response.ConnectionCountDTO;
import com.infy.network.dto.response.ConnectionResponseDTO;
import com.infy.network.dto.response.ConnectionStatusDTO;

public interface ConnectionService {

    Long sendConnectionRequest(ConnectionRequestDTO requestDTO);

    void updateConnectionRequestStatus(
            Long connectionId,
            ConnectionStatusUpdateDTO requestDTO
    );

    List<ConnectionResponseDTO> getPendingConnectionByUserId(Long userId);

    List<ConnectionResponseDTO> getConnectionByUserId(Long userId);

    void removeConnection(Long connectionId);

    ConnectionCountDTO getConnectionCount(Long userId);

    ConnectionStatusDTO getConnectionStatus(Long userId, Long targetUserId);
}
