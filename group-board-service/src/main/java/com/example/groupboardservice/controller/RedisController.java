package com.example.groupboardservice.controller;

import com.example.groupboardservice.service.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RedisController {

    private final RedisService redisService;

    @GetMapping("/redis")
    public String saveRedisString() {
        log.info(this.getClass().getName() + ".saveRedisString start");

        // 수집 결과 출력
        String msg;

        int response = redisService.saveRedisString();

        if (response == 1) {
            msg = "Success";
        } else {
            msg = "Fail";
        }

        log.info(this.getClass().getName() + ".saveRedisString end");
        return msg;
    }
}
