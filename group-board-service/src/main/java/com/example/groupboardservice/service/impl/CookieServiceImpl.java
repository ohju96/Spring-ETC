package com.example.groupboardservice.service.impl;

import com.example.groupboardservice.service.CookieService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Service
public class CookieServiceImpl implements CookieService {

    @Value("${jwt.token.refresh.valid.time}")
    private long refreshTokenValidTime;

    @Override
    public Cookie createCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setHttpOnly(true);
        cookie.setMaxAge((int) refreshTokenValidTime);
        cookie.setPath("/");
        return cookie;
    }

    @Override
    public Cookie getCookie(HttpServletRequest request, String key) {

        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(key)) {
                return cookie;
            }
        }

        return null;
    }
}
