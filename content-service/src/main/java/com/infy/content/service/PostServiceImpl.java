package com.infy.content.service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.infy.content.client.UserClient;
import com.infy.content.dto.request.PostCreateRequestDTO;
import com.infy.content.dto.request.PostStatusUpdateDTO;
import com.infy.content.dto.request.PostUpdateRequestDTO;
import com.infy.content.dto.response.PostResponseDTO;
import com.infy.content.dto.response.UserResponseDTO;
import com.infy.content.entity.Hashtag;
import com.infy.content.entity.Notification;
import com.infy.content.entity.Post;
import com.infy.content.entity.PostMention;
import com.infy.content.enums.NotificationType;
import com.infy.content.enums.PostStatus;
import com.infy.content.exception.InfyLinkedInException;
import com.infy.content.repository.HashtagRepository;
import com.infy.content.repository.NotificationRepository;
import com.infy.content.repository.PostRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

        private final PostRepository postRepository;
        private final HashtagRepository hashtagRepository;
        private final NotificationRepository notificationRepository;
        private final UserClient userClient;
        private final ModelMapper modelMapper;

        /* ================= CREATE POST ================= */

        @Override
        @Transactional
        public Long createPost(PostCreateRequestDTO dto) {

                userClient.validateUserExists(dto.getUserId());

                if (dto.getStatus() == PostStatus.SCHEDULED &&
                                (dto.getScheduledAt() == null ||
                                                dto.getScheduledAt().isBefore(LocalDateTime.now()))) {
                        throw new InfyLinkedInException("Scheduled time must be in future");
                }

                Post post = Post.builder()
                                .userId(dto.getUserId())
                                .content(dto.getContent())
                                .type(dto.getType())
                                .status(dto.getStatus())
                                .scheduledAt(dto.getScheduledAt())
                                .createdAt(LocalDateTime.now())
                                .build();

                /* ================= HASHTAGS ================= */
                if (dto.getHashtags() != null) {
                        Set<Hashtag> hashtags = dto.getHashtags().stream()
                                        .map(name -> hashtagRepository.findByNameIgnoreCase(name)
                                                        .orElseGet(() -> hashtagRepository.save(
                                                                        Hashtag.builder().name(name).build())))
                                        .collect(Collectors.toSet());
                        post.setHashtags(hashtags);
                }

                /* ================= MENTIONS ================= */
                if (dto.getMentionedUserIds() != null) {
                        userClient.getUsersByIds(dto.getMentionedUserIds());

                        List<PostMention> mentions = dto.getMentionedUserIds().stream()
                                        .map(uid -> {
                                                Notification notification = Notification.builder()
                                                                .userId(uid)
                                                                .type(NotificationType.POST)
                                                                .message("You were mentioned in a post")
                                                                .createdAt(LocalDateTime.now())
                                                                .build();

                                                notificationRepository.save(notification);

                                                return PostMention.builder()
                                                                .post(post)
                                                                .mentionedUserId(uid)
                                                                .build();
                                        }).toList();

                        post.setMentions(mentions);
                }

                post.setInteractions(Collections.emptyList());

                return postRepository.save(post).getId();
        }

        /* ================= UPDATE POST ================= */

        @Override
        @Transactional
        public void updatePost(Long postId, PostUpdateRequestDTO dto) {

                Post post = postRepository.findById(postId)
                                .orElseThrow(() -> new InfyLinkedInException("Post not found"));

                /* ================= UPDATE CONTENT ================= */
                post.setContent(dto.getContent());
                post.setUpdatedAt(LocalDateTime.now());

                /* ================= UPDATE HASHTAGS ================= */
                if (dto.getHashtags() != null) {

                        Set<Hashtag> updatedHashtags = dto.getHashtags().stream()
                                        .map(tag -> hashtagRepository.findByNameIgnoreCase(tag)
                                                        .orElseGet(() -> hashtagRepository.save(
                                                                        Hashtag.builder()
                                                                                        .name(tag)
                                                                                        .build())))
                                        .collect(Collectors.toSet());

                        /*
                         * IMPORTANT:
                         * - This clears only the JOIN TABLE (post_hashtags)
                         * - DOES NOT delete hashtag records
                         */
                        post.getHashtags().clear();
                        post.getHashtags().addAll(updatedHashtags);
                }

                /* ================= UPDATE MENTIONS ================= */
                if (dto.getMentionedUserIds() != null) {

                        // Existing mentioned user IDs
                        Set<Long> existingMentionedUserIds = post.getMentions().stream()
                                        .map(PostMention::getMentionedUserId)
                                        .collect(Collectors.toSet());

                        // New mentions only
                        List<Long> newMentions = dto.getMentionedUserIds().stream()
                                        .filter(id -> !existingMentionedUserIds.contains(id))
                                        .toList();

                        // Remove mentions not present anymore
                        post.getMentions().removeIf(
                                        mention -> !dto.getMentionedUserIds()
                                                        .contains(mention.getMentionedUserId()));

                        // Add new mentions + notifications
                        newMentions.forEach(userId -> {

                                Notification notification = Notification.builder()
                                                .userId(userId)
                                                .message("You were mentioned in an updated post")
                                                .type(NotificationType.POST)
                                                .createdAt(LocalDateTime.now())
                                                .build();

                                notificationRepository.save(notification);

                                post.getMentions().add(
                                                PostMention.builder()
                                                                .post(post)
                                                                .mentionedUserId(userId)
                                                                .build());
                        });
                }
        }

        /* ================= UPDATE STATUS ================= */

        @Override
        @Transactional
        public void updatePostStatus(Long postId, PostStatusUpdateDTO dto) {

                Post post = postRepository.findById(postId)
                                .orElseThrow(() -> new InfyLinkedInException("Post not found"));

                if (dto.getStatus() == PostStatus.SCHEDULED &&
                                dto.getScheduledAt() == null) {
                        throw new InfyLinkedInException("Schedule date required");
                }

                post.setStatus(dto.getStatus());
                post.setScheduledAt(dto.getScheduledAt());
                post.setUpdatedAt(LocalDateTime.now());
        }

        /* ================= READ OPERATIONS ================= */

        @Override
        public List<PostResponseDTO> getAllPosts() {
                return postRepository.findByStatus(PostStatus.PUBLISHED,Sort.by(Sort.Direction.DESC,"createdAt")).stream()
                                .map(this::mapToResponse)
                                .toList();
        }

        @Override
        public PostResponseDTO getPostByPostId(Long postId) {
                return mapToResponse(
                                postRepository.findById(postId)
                                                .orElseThrow(() -> new InfyLinkedInException("Post not found")));
        }

        @Override
        public List<PostResponseDTO> getPostByUserId(Long userId) {
                return postRepository.findByUserId(userId).stream()
                                .map(this::mapToResponse)
                                .toList();
        }

        @Override
        public List<PostResponseDTO> getPostByMentionedUserId(Long userId) {
                return postRepository.findByMentionedUserId(userId).stream()
                                .map(this::mapToResponse)
                                .toList();
        }

        /* ================= MAPPER ================= */

        private PostResponseDTO mapToResponse(Post post) {
                PostResponseDTO dto = modelMapper.map(post, PostResponseDTO.class);
                dto.setUser(userClient.getUsersById(post.getUserId()));
                dto.setHashtags(
                                post.getHashtags() == null ? Set.of()
                                                : post.getHashtags().stream()
                                                                .map(Hashtag::getName)
                                                                .collect(Collectors.toSet()));

                if (post.getMentions() != null) {
                        List<Long> mentionedUserIds = post.getMentions().stream()
                                        .map(PostMention::getMentionedUserId)
                                        .toList();
                        List<UserResponseDTO> mentionedUsers = userClient.getUsersByIds(mentionedUserIds);
                        dto.setMentionedUser(mentionedUsers);
                } else {
                        dto.setMentionedUser(Collections.emptyList());
                }

                return dto;
        }

        @Override
        @Transactional
        public void deletePostById(Long postId) {

                Post post = postRepository.findById(postId)
                                .orElseThrow(() -> new InfyLinkedInException("Post not found"));
                post.getHashtags().clear();
                /*
                 * CascadeType.ALL + orphanRemoval = true ensures:
                 * - post_mentions deleted
                 * - post_interactions deleted
                 * - post_hashtags join table cleaned automatically
                 */
                postRepository.delete(post);
        }
}
