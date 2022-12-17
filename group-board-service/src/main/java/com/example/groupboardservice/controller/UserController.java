package com.example.groupboardservice.controller;

import com.example.groupboardservice.data.request.CreateUserRequest;
import com.example.groupboardservice.data.response.ResponseDto;
import com.example.groupboardservice.exception.CustomException;
import com.example.groupboardservice.exception.enums.ExceptionEnum;
import com.example.groupboardservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import static com.example.groupboardservice.exception.enums.ExceptionEnum.APP_USER_NOT_FOUND;

@RestController
@Slf4j
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/test")
    public String testApi(@RequestParam String name) {
        if (name.isBlank()) {
            throw new CustomException(APP_USER_NOT_FOUND);
        }
        return name;
    }

    @PostMapping("/signup")
    public ResponseDto creatUser(CreateUserRequest user) {

        return new ResponseDto();
    }
}
