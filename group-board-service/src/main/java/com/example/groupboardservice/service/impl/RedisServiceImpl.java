package com.example.groupboardservice.service.impl;

import com.example.groupboardservice.data.dto.RedisDto;
import com.example.groupboardservice.repository.redis.RedisMapper;
import com.example.groupboardservice.service.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class RedisServiceImpl implements RedisService {

    private final RedisMapper redisMapper;

    @Override
    public int saveRedisString() {
        log.info(this.getClass().getName() + ".saveRedisString start");

        // 저장할 키
        String redisKey = "ohjuhyeon";

        // 저장할 데이터
        RedisDto redisDto = new RedisDto();
        redisDto.setTest_text("String으로 저장할 일반 문자열 테스트");

        int response = redisMapper.saveRedisString(redisKey, redisDto);

        log.info(this.getClass().getName() + ".saveRedisString end");
        return response;
    }
}
