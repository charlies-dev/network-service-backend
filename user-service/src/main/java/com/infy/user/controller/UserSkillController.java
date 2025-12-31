package com.infy.user.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
            @Valid @RequestBody String dto) {

        return ResponseEntity.ok(
                userSkillService.addUserSkill(userId, dto)
        );
    }

    @PostMapping("/user/{userId}/bulk")
    public ResponseEntity<List<Long>> addSkills(
            @PathVariable Long userId,
            @Valid @RequestBody List<String> dto) {

        return ResponseEntity.ok(
                userSkillService.addUserSkills(
                        userId,
                        dto
                )
        );
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
