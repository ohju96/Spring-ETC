package com.example.groupboardservice.repository.redis;

import com.example.groupboardservice.data.dto.RedisDto;

public interface RedisMapper {

    /**
     * String 타입 저장하기
     * @param redisKey Redis 저장 키
     * @param redisDto 저장할 정보
     * @return 저장 성공 여부
     */
    int saveRedisString(String redisKey, RedisDto redisDto);
}
