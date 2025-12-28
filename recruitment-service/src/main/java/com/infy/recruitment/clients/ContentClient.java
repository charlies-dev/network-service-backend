package com.infy.recruitment.clients;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.infy.recruitment.dto.request.NotificationCreateRequestDTO;
import com.infy.recruitment.enums.NotificationType;

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
                .uri("http://localhost:8082/notifications")
                .bodyValue(
                     new   NotificationCreateRequestDTO(userId,message,type)
                             
                )
                .retrieve()
                .toBodilessEntity()
                .block();
    }
}
