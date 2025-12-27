package com.infy.user.service;

import java.util.List;

import com.infy.user.dto.request.UpdateUserRequestDTO;
import com.infy.user.dto.request.UserDetailsRequestDTO;
import com.infy.user.dto.request.UserLoginRequestDTO;
import com.infy.user.dto.request.UserRegisterRequestDTO;
import com.infy.user.dto.response.UserResponseDTO;


public interface UserService {

   
    Long registerUser(UserRegisterRequestDTO requestDTO);

    UserResponseDTO loginUser(UserLoginRequestDTO requestDTO);

    void updateUser(Long userId, UpdateUserRequestDTO requestDTO);

    void updateVerifyUserStatus(Long userId, Boolean isVerified);

    void forgotPassword(Long userId, String newPassword);

    UserResponseDTO getUserById(Long userId);
    List<UserResponseDTO> getUserByIds(List<Long> userId);

    List<UserResponseDTO> getAllUsers();

    List<UserResponseDTO> searchUsersByName(String name);


    String addUserDetails(Long userId, UserDetailsRequestDTO requestDTO);
}
