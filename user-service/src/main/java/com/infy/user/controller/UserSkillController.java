package com.infy.user.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infy.user.dto.request.UserSkillListRequestDTO;
import com.infy.user.dto.request.UserSkillRequestDTO;
import com.infy.user.dto.response.UserResponseDTO;
import com.infy.user.dto.response.UserSkillResponseDTO;
import com.infy.user.entity.Skill;
import com.infy.user.service.SkillService;
import com.infy.user.service.UserSkillService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/skills")
@RequiredArgsConstructor
public class UserSkillController {

    private final UserSkillService userSkillService;
    private final SkillService skillService;

    @PostMapping("/user/{userId}")
    public ResponseEntity<Long> addSkill(
            @PathVariable Long userId,
            @Valid @RequestBody UserSkillRequestDTO dto) {

        return ResponseEntity.ok(
                userSkillService.addUserSkill(userId, dto)
        );
    }

    @PostMapping("/user/{userId}/bulk")
    public ResponseEntity<List<Long>> addSkills(
            @PathVariable Long userId,
            @Valid @RequestBody UserSkillListRequestDTO dto) {

        return ResponseEntity.ok(
                userSkillService.addUserSkills(
                        userId,
                        dto.getSkills()
                )
        );
    }

    @PutMapping("/{userSkillId}")
    public ResponseEntity<Void> updateSkill(
            @PathVariable Long userSkillId,
            @Valid @RequestBody UserSkillRequestDTO dto) {

        userSkillService.updateUserSkill(userSkillId, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{userSkillId}")
    public ResponseEntity<Void> deleteSkill(
            @PathVariable Long userSkillId) {

        userSkillService.removeUserSkill(userSkillId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UserSkillResponseDTO>> getByUser(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                userSkillService.getUserSkillDetailByUserId(userId)
        );
    }

    @GetMapping("/{skillId}/users")
    public ResponseEntity<List<UserResponseDTO>> getUsersBySkill(
            @PathVariable Long skillId) {

        return ResponseEntity.ok(
                userSkillService.getUsersBySkillId(skillId)
        );
    }
    @GetMapping("/")
    public ResponseEntity<List<Skill>> getAllSkill(
           ) {

        return ResponseEntity.ok(
                skillService.getAll()
        );
    }
}

