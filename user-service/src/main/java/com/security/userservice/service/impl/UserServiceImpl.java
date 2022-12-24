package com.security.userservice.service.impl;

import com.security.userservice.domain.Account;
import com.security.userservice.repository.UserRepository;
import com.security.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Transactional
    @Override
    public void createAccount(Account account) {

        userRepository.save(account);

    }
}
