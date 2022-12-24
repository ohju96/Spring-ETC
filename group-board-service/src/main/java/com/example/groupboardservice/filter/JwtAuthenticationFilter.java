package com.example.groupboardservice.filter;

import com.example.groupboardservice.config.auth.JwtTokenProvider;
import com.example.groupboardservice.data.request.LoginUserRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Value("${jwt.token.access.valid.time}")
    private long accessTokenValidTime;

    @Value("${jwt.token.access.name}")
    private String accessTokenName;

    private final JwtTokenProvider jwtTokenProvider;
    private AuthenticationManager authenticationManager;

    // 실행 제외 URL
    private final List<String> url = Collections.unmodifiableList(
        Arrays.asList(
            "/"
        )
    );

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain) throws AuthenticationException {

        ObjectMapper objectMapper = new ObjectMapper();
        LoginUserRequest loginUserRequest = null;
        try{
            loginUserRequest = objectMapper.readValue(request.getInputStream(), LoginUserRequest.class);
        } catch (Exception ex) {
            ex.printStackTrace();
        }


        assert loginUserRequest != null;
        UsernamePasswordAuthenticationToken authenticationToken
            = new UsernamePasswordAuthenticationToken(loginUserRequest.getId(), loginUserRequest.getPassword());

        return authenticationManager.authenticate(authenticationToken);

    }
}
