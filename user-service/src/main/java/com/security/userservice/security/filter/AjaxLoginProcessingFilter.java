package com.security.userservice.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.security.userservice.domain.AccountDto;
import com.security.userservice.security.token.AjaxAuthenticationToken;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.thymeleaf.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class AjaxLoginProcessingFilter extends AbstractAuthenticationProcessingFilter {

    private ObjectMapper objectMapper = new ObjectMapper();

    public AjaxLoginProcessingFilter() {
        super(new AntPathRequestMatcher("/api/login")); // 이 url로 요청이 와야한다.
    }

    // 실행 조건은 위 경로로 요청이 들어올 떄와 그 요청이 ajax일 때 실행된다.
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        log.info("ajax 요청 받아 실행");

        if (!isAjax(request)) {
            log.info("요청이 Ajax가 아님");
            throw new IllegalStateException("Authentication is not supported");
        }

        AccountDto accountDto = objectMapper.readValue(request.getReader(), AccountDto.class);

        if (StringUtils.isEmpty(accountDto.getUsername()) || StringUtils.isEmpty(accountDto.getPassword())) {
            throw new IllegalArgumentException("Username or Password is empty");
        }
        log.info("모든 예외 통과");

        //Ajax 인증 토큰을 만들어 사용자가 요청한 정보를 담고 인증 처리를 진행한다.
        AjaxAuthenticationToken ajaxAuthenticationToken = new AjaxAuthenticationToken(accountDto.getUsername(), accountDto.getPassword());

        // 인증 처리 진행
        return getAuthenticationManager().authenticate(ajaxAuthenticationToken);
    }

    private boolean isAjax(HttpServletRequest request) {
        log.info("isAjax 체크 시작 : {}", request.getHeader("X-Requested-With"));

        // 헤더 값(XMLHttpRequest)은 따로 정의해 준다. (약속) ajax일 때..
        if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
            return true;
        }
        return false;
    }
}
