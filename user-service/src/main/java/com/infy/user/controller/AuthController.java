package com.infy.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infy.user.dto.request.UserLoginRequestDTO;
import com.infy.user.dto.request.UserRegisterRequestDTO;
import com.infy.user.dto.response.ApiResponse;
import com.infy.user.dto.response.JwtResponse;
import com.infy.user.security.model.LinkedInUserDetails;
import com.infy.user.security.util.JwtUtils;
import com.infy.user.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    final AuthenticationManager authenticationManager;
    final JwtUtils jwtUtils;

    final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@Valid @RequestBody UserLoginRequestDTO request) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getEmailId(), request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtUtils.generateTokenForUser(authentication);
        LinkedInUserDetails userDetails = (LinkedInUserDetails) authentication.getPrincipal();
        JwtResponse jwtResponse = new JwtResponse(userDetails.getId(), jwt);
        return ResponseEntity.ok(new ApiResponse("success", jwtResponse));

    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> registerUser(@Valid @RequestBody UserRegisterRequestDTO request) {

        Long id = userService.registerUser(request);

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getEmailId(), request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtUtils.generateTokenForUser(authentication);
        LinkedInUserDetails userDetails = (LinkedInUserDetails) authentication.getPrincipal();
        JwtResponse jwtResponse = new JwtResponse(userDetails.getId(), jwt);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("success", jwtResponse));

    }
}
