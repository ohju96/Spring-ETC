package com.example.groupboardservice.repository.redis;

import com.example.groupboardservice.data.dto.RedisDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedisMapperImpl implements RedisMapper{

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public int saveRedisString(String redisKey, RedisDto redisDto) {
        log.info(this.getClass().getName() + ".saveRedisString start");

        int res = 0;

        // 저장할 값
        String saveData = redisDto.getTest_text();

        // Redis 저장 및 읽기에 대한 데이터 타입 String으로 지정
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());

        if (!redisTemplate.hasKey(redisKey)) { // 데이터가 존재하지 않으면 저장하기

            // 데이터 저장하기
            redisTemplate.opsForValue().set(redisKey, saveData);

            // Redis에 저장되는 데이터의 유효시간 설정 (TTL)
            // 1 분이 지나면 자동 삭제
            redisTemplate.expire(redisKey, 1, TimeUnit.MINUTES);

            res = 1;

            log.info("Save Data !!");
        }

        log.info(this.getClass().getName() + ".saveRedisString end");
        return res;
    }
}
