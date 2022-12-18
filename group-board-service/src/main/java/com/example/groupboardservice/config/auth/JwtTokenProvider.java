package com.example.groupboardservice.config.auth;

import com.example.groupboardservice.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Value("${jwt.secret.key}")
    private String secretKey;

    @Value("${jwt.token.creator}")
    private String creator;

    @Value("${jwt.token.access.valid.time}")
    private long accessTokenValidTime;

    @Value("${jwt.token.access.name}")
    private String accessTokenName;

    @Value("${jwt.token.refresh.valid.time}")
    private long refreshTokenValidTime;

    @Value("${jwt.token.refresh.name}")
    private String refreshTokenName;

    /**
     * JWT 토큰 (Access Token, Refresh Token) 생성
     * @param userId 회원 아이디
     * @param roles 회원 권한
     * @param tokenType 토큰 유형
     * @return 인증 처리한 정보(성공, 실패)
     */
    public String createToken(String userId, String roles, JwtTokenType tokenType) {
        log.info(this.getClass().getName() + ".createToken start !");
        log.info("userId : {}", userId);

        long validTime = 0;

        if (tokenType == JwtTokenType.ACCESS_TOKEN) {
            validTime = (accessTokenValidTime);
        } else if (tokenType == JwtTokenType.REFRESH_TOKEN) {
            validTime = (refreshTokenValidTime);
        }

        Claims claims = Jwts.claims()
                .setIssuer(creator) // JWT 토큰 생성자
                .setSubject(userId); // 회원 아이디 (PK) 저장

        claims.put("roles", roles); // JWT Payload에 정의된 기본 옵션 외 정보를 추가 : 사용자 권한 추가
        Date now = new Date();

        log.info(this.getClass().getName() + ".createToken end !");
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + (validTime * 1000)))
                .signWith(SignatureAlgorithm.HS256, secretKey) // 사용할 암호화 알고리즘과 키
                .compact();
    }

/*    public Authentication getAuthentication(String token) {
        log.info(this.getClass().getName() + ".getAuthentication start");
        log.info("getAuthentication : {}", token);

        // JWT 토큰에 저장된 사용자 아이디
        String userId = getUserId(token);
        log.info("userId : {}", userId);

//        userService.createUser();



    }*/


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

    /**
     * 쿠키에 저장된 JWT 토큰 가져오기
     * @param request 정보
     * @return 쿠키에 저장된 토큰 값
     */
    public String resolveToken(HttpServletRequest request) {
        log.info(this.getClass().getName() + ".resolveToken start");

        String token = "";

        // 쿠키에 저장된 데이터 모두 가져오기
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie key : request.getCookies()) {
                if (key.getName().equals(accessTokenName)) {
                    token = key.getValue();
                    break;
                }
            }
        }

        log.info(this.getClass().getName() + ".resolveToken end");
        return token;
    }


}
