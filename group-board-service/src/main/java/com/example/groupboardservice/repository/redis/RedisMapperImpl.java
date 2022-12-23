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

    @Override
    public RedisDto getRedisString(String redisKey) {
        log.info(this.getClass().getName() + ".getRedisString start");
        log.info("String redisKey : {}", redisKey);

        RedisDto redisDto = new RedisDto();

        // 저장 타입과 동일하게 맞춰준다.
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());

        if (redisTemplate.hasKey(redisKey)) { // 데이터가 존재하면
            // Redis에서 가져오기
            String response = (String) redisTemplate.opsForValue().get(redisKey);
            log.info("response : {}", response);

            // Redis에 저장된 데이터를 DTO에 저장
            redisDto.setTest_text(response);
        }

        log.info(this.getClass().getName() + ".getRedisString end");
        return redisDto;
    }

    @Override
    public boolean saveRefreshToken(String key, String value, long time) {

        boolean check = false;

        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());

        // 데이터가 없으면 저장
        if (!redisTemplate.hasKey(key)) {
            String res = (String) redisTemplate.opsForValue().get(key);

            // 데이터 저장하기
            redisTemplate.opsForValue().set(key, value);

            // Redis에 저장되는 데이터의 유효시간 설정 (TTL)
            // 1 분이 지나면 자동 삭제
            redisTemplate.expire(key, time, TimeUnit.SECONDS);

            check = true;
        }

        return check;
    }
}
