package com.example.groupboardservice.controller;

import com.example.groupboardservice.exception.CustomException;
import com.example.groupboardservice.exception.enums.ExceptionEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.example.groupboardservice.exception.enums.ExceptionEnum.APP_USER_NOT_FOUND;

@RestController
@Slf4j
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    @GetMapping("/test")
    public String testApi(@RequestParam String name) {
        if (name.isBlank()) {
            throw new CustomException(APP_USER_NOT_FOUND);
        }
        return name;
    }
}
