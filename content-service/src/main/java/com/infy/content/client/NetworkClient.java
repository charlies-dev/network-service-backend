package com.infy.content.client;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.infy.content.dto.response.ConnectionCountDTO;
import com.infy.content.dto.response.ConnectionResponseDTO;
import com.infy.content.exception.InfyLinkedInException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class NetworkClient {

    private final WebClient.Builder webClientBuilder;

    public ConnectionCountDTO getConnectionCount(Long userId) {
        try {
            return webClientBuilder.build()
                    .get()
                   .uri("http://network-service/connections/count/{userId}", userId)
                    .retrieve()
                    .bodyToMono(ConnectionCountDTO.class)
                    .block();
        } catch (Exception ex) {
            throw new InfyLinkedInException("Unable to fetch connection count");
        }
    }

    public List<Long> getConnectedUserIds(Long userId) {
        try {
            List<ConnectionResponseDTO> connections = webClientBuilder.build()
                    .get()
                      .uri("http://network-service/connections/user/{userId}", userId)
                    .retrieve()
                    .bodyToFlux(ConnectionResponseDTO.class)
                    .collectList()
                    .block();

            List<Long> connectedUserIds = new ArrayList<>();
            if (connections != null) {
                connections.forEach(connection -> {
                    Long connectedId = connection.getConnectedUser().getId();
                    connectedUserIds.add(connectedId);
                });
            }
            return connectedUserIds;
        } catch (Exception ex) {
            throw new InfyLinkedInException("Unable to fetch connections");
        }
    }
}
