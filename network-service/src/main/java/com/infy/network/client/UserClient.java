package com.infy.network.client;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.infy.network.dto.response.UserDetailsDTO;
import com.infy.network.dto.response.UserResponseDTO;
import com.infy.network.exception.InfyLinkedInException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserClient {

    private final WebClient.Builder webClientBuilder;

    public void validateUserExists(Long userId) {
        try {
            webClientBuilder.build()
                    .get()
                    .uri("http://user-service/users/{id}", userId)
                    .retrieve()
                    .toBodilessEntity()
                    .block();
        } catch (Exception ex) {
            throw new InfyLinkedInException("User does not exist");
        }
    }

    public List<UserResponseDTO> getUsersByIds(List<Long> userIds) {
        try {
            return webClientBuilder.build()
                    .post()
                    .uri("http://user-service/users/ids")
                    .bodyValue(userIds)
                    .retrieve()
                    .bodyToFlux(UserResponseDTO.class)
                    .collectList()
                    .block();
        } catch (Exception ex) {
            throw new InfyLinkedInException("Unable to fetch user name");
        }
    }

    public UserResponseDTO getUsersById(Long userId) {
        try {
            return webClientBuilder.build()
                    .get()
                    .uri("http://user-service/users/{id}", userId)
                    .retrieve()
                    .bodyToMono(UserResponseDTO.class)

                    .block();
        } catch (Exception ex) {
            throw new InfyLinkedInException("Unable to fetch user name");
        }
    }

    public UserDetailsDTO getUserDetailsById(Long userId) {
        try {
            UserResponseDTO userEntity = webClientBuilder.build()
                    .get()
                    .uri("http://user-service/users/{id}", userId)
                    .retrieve()
                    .bodyToMono(UserResponseDTO.class)

                    .block();
            UserDetailsDTO user = new UserDetailsDTO();

            user.setId(userEntity.getId());
            user.setFirstName(userEntity.getFirstName());
            user.setLastName(userEntity.getLastName());
            user.setEmailId(userEntity.getEmailId());
            user.setProfilePhotoUrl(userEntity.getProfile().getProfilePhotoUrl());
            return user;

        } catch (Exception ex) {
            throw new InfyLinkedInException("Unable to fetch user name");
        }
    }

}