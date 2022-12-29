package com.security.userservice.security.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        log.info("failure handler start");
        String errorMessage = "Invalid Username or Password";

        if (exception instanceof BadCredentialsException) { // 예외가 BadCer..이면
            log.info("BadCredentialsException");
            errorMessage = "Invalid Username or Password";
        } else if (exception instanceof InsufficientAuthenticationException) {
            log.info("InsufficientAuthenticationException");
            errorMessage = "Invalid Secret key";
        }

        // 예외 메시지를 로그인 페이지로 전달한다.
        // 사용자가 인증에 실패한 원인을 알게 된다.
        // 해당 경로를 스프링 시큐리티에서 퍼밋올 해 줘야 한다. ex) /login*
        setDefaultFailureUrl("/login?error=true&exception=" + exception.getMessage());

        super.onAuthenticationFailure(request, response, exception);
        log.info("failure handler end");
    }
}
