package com.security.userservice.security.config;

import com.security.userservice.security.filter.AjaxLoginProcessingFilter;
import com.security.userservice.security.handler.CustomAccessDeniedHandler;
import com.security.userservice.security.provider.CustomAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    private final AuthenticationDetailsSource authenticationDetailsSource;
    private final AuthenticationSuccessHandler customAuthenticationSuccessHandler; //커스텀 로그인 핸들러
    private final AuthenticationFailureHandler customAuthenticationFailureHandler;

/*    // 임시 유저 생성, 회원가입 기능 만들고 나서 주석 처리
    @Bean
    public UserDetailsManager users() {
        String password = passwordEncoder().encode("1111");

        UserDetails user = User.builder()
                .username("user")
                .password(password)
                .roles("USER")
                .build();

        UserDetails manager = User.builder()
                .username("manager")
                .password(password)
                .roles("MANAGER", "USER")
                .build();

        UserDetails admin = User.builder()
                .username("admin")
                .password(password)
                .roles("USER", "MANAGER", "ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user, manager, admin);
    }*/

    // 비밀번호 암호화
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    // 정적 파일에 대해 보안 검증을 실행하지 않고 통과
    // 시큐리티 필터 체인 구현에 있는 permitALl은 보안 검증은 거침
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> {
            web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
            web.ignoring().antMatchers("/favicon.ico", "resources/**", "/error");
        };
    }

    // CustomUserDetails를 사용해서 인증 처리하도록 설정
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfiguration) throws Exception {
        return authConfiguration.getAuthenticationManager();
    }

    // CustomProvider 사용을 위한 설정
    @Bean
    public CustomAuthenticationProvider customAuthenticationProvider() {
        return new CustomAuthenticationProvider(userDetailsService, passwordEncoder());
    }

    // 인증 거부 처리를 위한 핸들러
    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        CustomAccessDeniedHandler accessDeniedHandler = new CustomAccessDeniedHandler();
        accessDeniedHandler.setErrorPage("/denied");
        return accessDeniedHandler;
    }

    // 시큐리티 필터 체인 구현
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/", "/users", "user/login/**", "/login*").permitAll()
                .antMatchers("/mypage").hasRole("USER")
                .antMatchers("/messages").hasRole("MANAGER")
                .antMatchers("/config").hasRole("ADMIN")
            .anyRequest().authenticated()
        ;
        http
            .formLogin()
            .loginPage("/login") // 로그인 경로
            .loginProcessingUrl("/login_proc") // 로그인 페이지의 action 값
            .defaultSuccessUrl("/") // 로그인 성공 후 이동하는 경로
            .authenticationDetailsSource(authenticationDetailsSource) // 파라미터 설정 및 재생
            .successHandler(customAuthenticationSuccessHandler) // 로그인 성공 커스텀 핸들러
            .failureHandler(customAuthenticationFailureHandler) // 로그인 실패 커스텀 핸들
            .permitAll()
        ; // 로그인 페이지는 인증 받지 않은 사용자도 접근 가능하다.
        http
                .logout()
        ;
        http
            .exceptionHandling()
            .accessDeniedHandler(accessDeniedHandler())
        ;
        http
            // 기존 필터 앞에서 처리하기 위해 before 필터 사용
            .addFilterBefore(new AjaxLoginProcessingFilter(), UsernamePasswordAuthenticationFilter.class)
        ;
        http
            .csrf().disable()
            ;
        return http.build();
    }
}
