package com.example.groupboardservice.service;

import com.example.groupboardservice.data.dto.RedisDto;

public interface RedisService {

    /**
     * String 타입 저장
     * @return 성공 여부
     */
    int saveRedisString();

    /**
     * String 타입 가져오기
     * @return 저장 값
     */
    RedisDto getRedisString();
}
