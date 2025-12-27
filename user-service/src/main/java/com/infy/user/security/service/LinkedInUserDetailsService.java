package com.infy.user.security.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.infy.user.entity.User;
import com.infy.user.repository.UserRepository;
import com.infy.user.security.model.LinkedInUserDetails;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LinkedInUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmailId(email)
                .orElseThrow(() -> new UsernameNotFoundException("user nor found"));
        return LinkedInUserDetails.buildUserDetails(user);
    }

}
