package com.infy.network.client;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.infy.network.dto.request.NotificationCreateRequestDTO;
import com.infy.network.enums.NotificationType;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ContentClient {

    private final WebClient.Builder webClientBuilder;

    public void sendNotification(
            Long userId,
            String message,
            NotificationType type) {

        webClientBuilder.build()
                .post()
                 .uri("http://content-service/notifications")
                .bodyValue(
                     new   NotificationCreateRequestDTO(userId,message,type)
                             
                )
                .retrieve()
                .toBodilessEntity()
                .block();
    }
}
