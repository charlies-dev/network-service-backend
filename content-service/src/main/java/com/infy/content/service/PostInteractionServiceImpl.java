package com.infy.content.service;

import java.time.LocalDateTime;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.infy.content.client.UserClient;
import com.infy.content.dto.request.PostInteractionRequestDTO;
import com.infy.content.dto.request.PostInteractionUpdateDTO;
import com.infy.content.dto.response.PostInteractionMatrixDTO;
import com.infy.content.dto.response.PostInteractionResponseDTO;
import com.infy.content.dto.response.UserDetailsDTO;
import com.infy.content.dto.response.UserResponseDTO;
import com.infy.content.entity.Notification;
import com.infy.content.entity.Post;
import com.infy.content.entity.PostInteraction;
import com.infy.content.enums.InteractionType;
import com.infy.content.enums.NotificationType;
import com.infy.content.exception.InfyLinkedInException;
import com.infy.content.repository.NotificationRepository;
import com.infy.content.repository.PostInteractionRepository;
import com.infy.content.repository.PostRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostInteractionServiceImpl implements PostInteractionService {

    private final PostInteractionRepository interactionRepository;
    private final PostRepository postRepository;
    private final NotificationRepository notificationRepository;
    private final UserClient userClient;
    private final ModelMapper modelMapper;

    /* ================= ADD INTERACTION ================= */

    @Override
    @Transactional
    public Long addPostInteraction(
            Long postId,
            PostInteractionRequestDTO dto) {

        userClient.validateUserExists(dto.getUserId());

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new InfyLinkedInException("Post not found"));

        PostInteraction interaction = PostInteraction.builder()
                .post(post)
                .userId(dto.getUserId())
                .type(dto.getType())
                .commentText(dto.getCommentText())
                .createdAt(LocalDateTime.now())
                .build();

        PostInteraction saved = interactionRepository.save(interaction);

        /* ===== Notification to Post Owner ===== */
        Notification notification = Notification.builder()
                .userId(post.getUserId())
                .type(NotificationType.INTERACTION)
                .message("Your post received a new interaction " + saved.getType().toString())
                .createdAt(LocalDateTime.now())
                .build();

        notificationRepository.save(notification);

        return saved.getId();
    }

    /* ================= UPDATE INTERACTION ================= */

    @Override
    @Transactional
    public void updatePostInteraction(
            Long interactionId,
            PostInteractionUpdateDTO dto) {

        PostInteraction interaction = interactionRepository.findById(interactionId)
                .orElseThrow(() -> new InfyLinkedInException("Post interaction not found"));

        if (interaction.getType() != InteractionType.COMMENT) {
            throw new InfyLinkedInException("Only comments can be updated");
        }

        interaction.setCommentText(dto.getCommentText());
    }

    /* ================= REMOVE INTERACTION ================= */

    @Override
    @Transactional
    public void removePostInteraction(Long interactionId) {

        PostInteraction interaction = interactionRepository.findById(interactionId)
                .orElseThrow(() -> new InfyLinkedInException("Post interaction not found"));

        interactionRepository.delete(interaction);
    }

    /* ================= GET BY POST ================= */

    @Override
    public List<PostInteractionResponseDTO> getPostInteractionByPostId(Long postId) {

        if (!postRepository.existsById(postId)) {
            throw new InfyLinkedInException("Post not found");
        }

        return interactionRepository.findByPostId(postId,Sort.by(Sort.Direction.DESC, "createdAt"))
                .stream()
                .map(interaction -> {
                    PostInteractionResponseDTO dto = modelMapper.map(interaction, PostInteractionResponseDTO.class);
                    dto.setPostId(postId);
                    UserDetailsDTO user = new UserDetailsDTO();
                    UserResponseDTO responseDTO = userClient.getUsersById(interaction.getUserId());
                    user.setId(responseDTO.getId());
                    user.setFirstName(responseDTO.getFirstName());
                    user.setLastName(responseDTO.getLastName());
                    user.setEmailId(responseDTO.getEmailId());
                    user.setProfilePhotoUrl(responseDTO.getProfile().getProfilePhotoUrl());
                    return dto;
                })
                .toList();
    }

    /* ================= MATRIX ================= */

    @Override
    public PostInteractionMatrixDTO getPostInteractionMatrixByPostId(Long postId) {

        if (!postRepository.existsById(postId)) {
            throw new InfyLinkedInException("Post not found");
        }

        long likeCount = interactionRepository.countByPostIdAndType(postId, InteractionType.LIKE);

        long commentCount = interactionRepository.countByPostIdAndType(postId, InteractionType.COMMENT);

        return new PostInteractionMatrixDTO(likeCount, commentCount);
    }

    @Override
    public List<UserDetailsDTO> getUsersByInteractionType(
            Long postId,
            InteractionType interactionType) {

        if (!postRepository.existsById(postId)) {
            throw new InfyLinkedInException("Post not found");
        }

        List<PostInteraction> interactions = interactionRepository.findByPostIdAndType(postId, interactionType, Sort.by(Sort.Direction.DESC,"createdAt"));

        if (interactions.isEmpty()) {
            throw new InfyLinkedInException(
                    "No users found for given interaction type");
        }

        // Extract distinct userIds
        List<Long> userIds = interactions.stream()
                .map(PostInteraction::getUserId)
                .distinct()
                .toList();

        // Fetch users from User Service
        return userClient.getUsersByIds(userIds).stream().map(userEntity -> {
            UserDetailsDTO user = new UserDetailsDTO();

            user.setId(userEntity.getId());
            user.setFirstName(userEntity.getFirstName());
            user.setLastName(userEntity.getLastName());
            user.setEmailId(userEntity.getEmailId());
            user.setProfilePhotoUrl(userEntity.getProfile().getProfilePhotoUrl());
            return user;
        }).toList();
    }

}
