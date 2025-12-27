package com.infy.content.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.infy.content.dto.request.PostInteractionRequestDTO;
import com.infy.content.dto.request.PostInteractionUpdateDTO;
import com.infy.content.dto.response.PostInteractionMatrixDTO;
import com.infy.content.dto.response.PostInteractionResponseDTO;
import com.infy.content.dto.response.UserDetailsDTO;
import com.infy.content.enums.InteractionType;
import com.infy.content.service.PostInteractionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/interactions")
@RequiredArgsConstructor
public class PostInteractionController {

    private final PostInteractionService interactionService;

    @PostMapping("/post/{postId}")
    public ResponseEntity<Long> addInteraction(
            @PathVariable Long postId,
            @Valid @RequestBody PostInteractionRequestDTO dto) {

        return ResponseEntity.ok(
                interactionService.addPostInteraction(postId, dto));
    }

    @PutMapping("/{interactionId}")
    public ResponseEntity<Void> updateInteraction(
            @PathVariable Long interactionId,
            @Valid @RequestBody PostInteractionUpdateDTO dto) {

        interactionService.updatePostInteraction(interactionId, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{interactionId}")
    public ResponseEntity<Void> deleteInteraction(
            @PathVariable Long interactionId) {

        interactionService.removePostInteraction(interactionId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<PostInteractionResponseDTO>> getByPost(
            @PathVariable Long postId) {

        return ResponseEntity.ok(
                interactionService.getPostInteractionByPostId(postId));
    }

    @GetMapping("/post/{postId}/matrix")
    public ResponseEntity<PostInteractionMatrixDTO> getMatrix(
            @PathVariable Long postId) {

        return ResponseEntity.ok(
                interactionService.getPostInteractionMatrixByPostId(postId));
    }

    @GetMapping("/post/{postId}/users")
    public ResponseEntity<List<UserDetailsDTO>> getUsersByInteractionType(
            @PathVariable Long postId,
            @RequestParam InteractionType type) {

        return ResponseEntity.ok(
                interactionService.getUsersByInteractionType(postId, type));
    }
}
