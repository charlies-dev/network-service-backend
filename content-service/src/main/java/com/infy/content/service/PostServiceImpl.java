package com.infy.content.service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.infy.content.client.NetworkClient;
import com.infy.content.client.UserClient;
import com.infy.content.dto.request.PostCreateRequestDTO;
import com.infy.content.dto.request.PostStatusUpdateDTO;
import com.infy.content.dto.request.PostUpdateRequestDTO;
import com.infy.content.dto.response.PostCountDTO;
import com.infy.content.dto.response.PostResponseDTO;
import com.infy.content.dto.response.UserDetailsDTO;
import com.infy.content.dto.response.UserResponseDTO;
import com.infy.content.dto.response.UserStatsDTO;
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
        private final NetworkClient networkClient;
        private final ModelMapper modelMapper;

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

                if (dto.getHashtags() != null) {
                        Set<Hashtag> hashtags = dto.getHashtags().stream()
                                        .map(name -> hashtagRepository.findByNameIgnoreCase(name)
                                                        .orElseGet(() -> hashtagRepository.save(
                                                                        Hashtag.builder().name(name).build())))
                                        .collect(Collectors.toSet());
                        post.setHashtags(hashtags);
                }

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

        @Override
        @Transactional
        public void updatePost(Long postId, PostUpdateRequestDTO dto) {

                Post post = postRepository.findById(postId)
                                .orElseThrow(() -> new InfyLinkedInException("Post not found"));

                post.setContent(dto.getContent());
                post.setUpdatedAt(LocalDateTime.now());

                if (dto.getHashtags() != null) {

                        Set<Hashtag> updatedHashtags = dto.getHashtags().stream()
                                        .map(tag -> hashtagRepository.findByNameIgnoreCase(tag)
                                                        .orElseGet(() -> hashtagRepository.save(
                                                                        Hashtag.builder()
                                                                                        .name(tag)
                                                                                        .build())))
                                        .collect(Collectors.toSet());

                        post.getHashtags().clear();
                        post.getHashtags().addAll(updatedHashtags);
                }

                if (dto.getMentionedUserIds() != null) {

                        Set<Long> existingMentionedUserIds = post.getMentions().stream()
                                        .map(PostMention::getMentionedUserId)
                                        .collect(Collectors.toSet());

                        List<Long> newMentions = dto.getMentionedUserIds().stream()
                                        .filter(id -> !existingMentionedUserIds.contains(id))
                                        .toList();

                        post.getMentions().removeIf(
                                        mention -> !dto.getMentionedUserIds()
                                                        .contains(mention.getMentionedUserId()));

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

        private PostResponseDTO mapToResponse(Post post) {
                PostResponseDTO dto = modelMapper.map(post, PostResponseDTO.class);
                dto.setUser(userClient.getUserDetailsById(post.getUserId()));
                dto.setHashtags(
                                post.getHashtags() == null ? Set.of()
                                                : post.getHashtags().stream()
                                                                .map(Hashtag::getName)
                                                                .collect(Collectors.toSet()));

                if (post.getMentions() != null) {
                        List<Long> mentionedUserIds = post.getMentions().stream()
                                        .map(PostMention::getMentionedUserId)
                                        .toList();
                        List<UserDetailsDTO> mentionedUsers = userClient.getUsersDetailsByIds(mentionedUserIds);
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
               
                postRepository.delete(post);
        }

        @Override
        public PostCountDTO getPostCount(Long userId) {
                userClient.validateUserExists(userId);

                Long totalPostCount = postRepository.countByUserId(userId);
                Long publishedCount = postRepository.countByUserIdAndStatus(userId, PostStatus.PUBLISHED);
                Long draftCount = postRepository.countByUserIdAndStatus(userId, PostStatus.DRAFT);

                return new PostCountDTO(
                        userId,
                        totalPostCount != null ? totalPostCount : 0L,
                        publishedCount != null ? publishedCount : 0L,
                        draftCount != null ? draftCount : 0L
                );
        }

        @Override
        public UserStatsDTO getUserStats(Long userId) {
                userClient.validateUserExists(userId);

                Long totalPostCount = postRepository.countByUserId(userId);
                Long publishedCount = postRepository.countByUserIdAndStatus(userId, PostStatus.PUBLISHED);
                Long draftCount = postRepository.countByUserIdAndStatus(userId, PostStatus.DRAFT);

                var connectionData = networkClient.getConnectionCount(userId);

                return new UserStatsDTO(
                        userId,
                        connectionData.getConnectionCount() != null ? connectionData.getConnectionCount() : 0L,
                        connectionData.getPendingRequestCount() != null ? connectionData.getPendingRequestCount() : 0L,
                        totalPostCount != null ? totalPostCount : 0L,
                        publishedCount != null ? publishedCount : 0L,
                        draftCount != null ? draftCount : 0L
                );
        }

        @Override
        public List<PostResponseDTO> getConnectedUsersPosts(Long userId) {
                userClient.validateUserExists(userId);

                List<Long> connectedUserIds = networkClient.getConnectedUserIds(userId);

                if (connectedUserIds == null || connectedUserIds.isEmpty()) {
                        return Collections.emptyList();
                }

                List<PostResponseDTO> connectedUsersPosts = new java.util.ArrayList<>();

                for (Long connectedUserId : connectedUserIds) {
                        List<Post> userPosts = postRepository.findByUserIdAndStatus(connectedUserId, PostStatus.PUBLISHED);

                        userPosts.stream()
                                .map(post -> {
                                        PostResponseDTO postDTO = modelMapper.map(post, PostResponseDTO.class);
                                        UserDetailsDTO userDTO = userClient.getUserDetailsById(post.getUserId());
                                        postDTO.setUser(userDTO);
                                        return postDTO;
                                })
                                .forEach(connectedUsersPosts::add);
                }

                connectedUsersPosts.sort((p1, p2) -> p2.getCreatedAt().compareTo(p1.getCreatedAt()));

                return connectedUsersPosts;
        }
}
