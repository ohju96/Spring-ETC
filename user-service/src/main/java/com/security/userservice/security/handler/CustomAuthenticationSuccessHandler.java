package com.security.userservice.security.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private RequestCache requestCache = new HttpSessionRequestCache();
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        setDefaultTargetUrl("/");

        // 요청 캐시에서 요청 경로를 가져와서
        SavedRequest saveRequest = requestCache.getRequest(request, response);
        log.info("saveRequest : {}", saveRequest);

        // 경로가 있다면 요청 경로로 이동
        if (saveRequest != null) {
            log.info("캐시 경로 있음");
            String targetUrl = saveRequest.getRedirectUrl();
            log.info("targetUrl : {}", targetUrl);
            redirectStrategy.sendRedirect(request, response, targetUrl);
        } else { // 없다면 기본 "/" 경로로 이동
            log.info("캐시 경로 없음");
            redirectStrategy.sendRedirect(request, response, getDefaultTargetUrl());
        }
    }
}
