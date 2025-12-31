package com.infy.content.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infy.content.dto.request.PostCreateRequestDTO;
import com.infy.content.dto.request.PostStatusUpdateDTO;
import com.infy.content.dto.request.PostUpdateRequestDTO;
import com.infy.content.dto.response.PostCountDTO;
import com.infy.content.dto.response.PostResponseDTO;
import com.infy.content.dto.response.UserStatsDTO;
import com.infy.content.service.PostService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<Long> create(@Valid @RequestBody PostCreateRequestDTO dto) {
        return ResponseEntity.ok(postService.createPost(dto));
    }

    @PutMapping("/{postId}")
    public ResponseEntity<Void> update(
            @PathVariable Long postId,
            @RequestBody PostUpdateRequestDTO dto) {
        postService.updatePost(postId, dto);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{postId}/status")
    public ResponseEntity<Void> updateStatus(
            @PathVariable Long postId,
            @Valid @RequestBody PostStatusUpdateDTO dto) {
        postService.updatePostStatus(postId, dto);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<PostResponseDTO>> getAll() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostResponseDTO> getById(@PathVariable Long postId) {
        return ResponseEntity.ok(postService.getPostByPostId(postId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PostResponseDTO>> getByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(postService.getPostByUserId(userId));
    }

    @GetMapping("/mentions/{userId}")
    public ResponseEntity<List<PostResponseDTO>> getByMention(
            @PathVariable Long userId) {
        return ResponseEntity.ok(postService.getPostByMentionedUserId(userId));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(
            @PathVariable Long postId) {

        postService.deletePostById(postId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/count/{userId}")
    public ResponseEntity<PostCountDTO> getPostCount(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                postService.getPostCount(userId)
        );
    }

    @GetMapping("/stats/{userId}")
    public ResponseEntity<UserStatsDTO> getUserStats(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                postService.getUserStats(userId)
        );
    }

    @GetMapping("/feed/{userId}")
    public ResponseEntity<List<PostResponseDTO>> getConnectedUsersPosts(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                postService.getConnectedUsersPosts(userId)
        );
    }
}
