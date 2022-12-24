package com.example.groupboardservice.service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public interface CookieService {

    /**
     * 쿠키 저장
     * @param key 토큰 이름 ex) accessToken, refreshToken
     * @param value 실제 토큰
     * @return 쿠키
     */
    Cookie createCookie(String key, String value);

    /**
     * 쿠키 가져오기
     * @param request 서블릿 리퀘스트
     * @param key 토큰 이름 ex) accessToken, refreshToken
     * @return 쿠키
     */
    Cookie getCookie(HttpServletRequest request, String key);
}
