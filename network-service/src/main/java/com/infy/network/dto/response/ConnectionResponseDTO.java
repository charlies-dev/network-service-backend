package com.infy.network.dto.response;

import java.time.LocalDateTime;

import com.infy.network.enums.ConnectionStatus;

import lombok.Data;

@Data
public class ConnectionResponseDTO {

    private Long id;
    private UserDetailsDTO user;
    private UserDetailsDTO connectedUser;
    private ConnectionStatus status;
    private LocalDateTime createdAt;
}
