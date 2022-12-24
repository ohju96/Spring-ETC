package com.example.groupboardservice.controller;

import com.example.groupboardservice.data.dto.JwtTokenDto;
import com.example.groupboardservice.data.request.CreateUserRequest;
import com.example.groupboardservice.data.request.LoginUserRequest;
import com.example.groupboardservice.data.response.CustomResponseDto;
import com.example.groupboardservice.data.response.ResponseDto;
import com.example.groupboardservice.data.response.TokenResponse;
import com.example.groupboardservice.exception.CustomException;
import com.example.groupboardservice.repository.redis.RedisMapper;
import com.example.groupboardservice.service.CookieService;
import com.example.groupboardservice.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.example.groupboardservice.exception.enums.ExceptionEnum.APP_USER_NOT_FOUND;

@RestController
@Slf4j
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "User", description = "유저 API")
public class UserController {

    private final UserService userService;
    private final CookieService cookieService;
    private final RedisMapper redisMapper;

    @Value("${jwt.token.refresh.valid.time}")
    private long refreshTokenValidTime;

    // 회원가입
    @Tag(name = "User")
    @PostMapping("/signup")
    public ResponseDto createUser(@RequestBody CreateUserRequest user) {
        log.info(this.getClass().getName() + ".createUser start");

        userService.createUser(user);

        log.info(this.getClass().getName() + ".createUser end");
        return new ResponseDto();
    }

    // 로그인
    @Tag(name = "User")
    @PostMapping("/login")
    public ResponseDto login(@RequestBody LoginUserRequest user,
                             HttpServletResponse response) {
        log.info(this.getClass().getName() + ".login start");

        // 여기서 리턴 값으로 엑세스 토큰을 받아서 사용해야 한다.
        JwtTokenDto jwtToken = userService.loginUser(user);

        Cookie refreshToken = cookieService.createCookie("refreshToken", jwtToken.getRefreshToken());

        redisMapper.saveRefreshToken("refreshToken", jwtToken.getRefreshToken(), refreshTokenValidTime);

        log.info(this.getClass().getName() + ".login end");
        return new CustomResponseDto<>(TokenResponse.accessTokenResponse(jwtToken.getAccessToken()));
    }

    @GetMapping("/test")
    public String test() {
        return "Success";
    }
}
