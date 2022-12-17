package com.example.groupboardservice.service.impl;

import com.example.groupboardservice.config.auth.RoleType;
import com.example.groupboardservice.data.domain.users.User;
import com.example.groupboardservice.data.request.CreateUserRequest;
import com.example.groupboardservice.repository.UserRepository;
import com.example.groupboardservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public void createUser(CreateUserRequest user) {
        userRepository.save(User.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .roles(RoleType.USER.getRole())
                .build());
    }
}
