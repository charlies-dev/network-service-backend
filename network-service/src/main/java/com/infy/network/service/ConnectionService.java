package com.infy.network.service;

import com.infy.network.dto.request.ConnectionRequestDTO;
import com.infy.network.dto.request.ConnectionStatusUpdateDTO;
import com.infy.network.dto.response.ConnectionResponseDTO;

import java.util.List;

public interface ConnectionService {

    Long sendConnectionRequest(ConnectionRequestDTO requestDTO);

    void updateConnectionRequestStatus(
            Long connectionId,
            ConnectionStatusUpdateDTO requestDTO
    );

    List<ConnectionResponseDTO> getPendingConnectionByUserId(Long userId);

    List<ConnectionResponseDTO> getConnectionByUserId(Long userId);

    void removeConnection(Long connectionId);
}
