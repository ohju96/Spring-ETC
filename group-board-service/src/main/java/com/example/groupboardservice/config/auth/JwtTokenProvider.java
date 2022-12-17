package com.example.groupboardservice.config.auth;

import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Value("${jwt.secret.key}")
    private String secretKey;

    @Value("${jwt.token.access.name}")
    private String accessTokenName;

    /**
     * JWT 토큰(Access Token, Refresh Token)에서 회원 정보 추출
     * @param token 토큰
     * @return 회원 아이디
     */
    public String getUserId(String token) {
        log.info(this.getClass().getName() + ".getUserId start");

        String userId = Jwts.parser().setSigningKey(secretKey)
            .parseClaimsJws(token).getBody().getSubject();
        log.info("userId : {}", userId);

        log.info(this.getClass().getName() + ".getUserId end");
        return userId;
    }

//    public String resolveToken(HttpServletRequest request)


}
