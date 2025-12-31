package com.infy.content.dto.response;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ConnectionResponseDTO {

    private Long id;
    private UserDetailsDTO user;
    private UserDetailsDTO connectedUser;
    private String status;
    private LocalDateTime createdAt;
}
