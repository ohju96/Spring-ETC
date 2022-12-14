package com.security.userservice.security.provider;

import com.security.userservice.security.common.FormAuthenticationDetailsSource;
import com.security.userservice.security.common.FormWebAuthenticationDetails;
import com.security.userservice.security.service.AccountContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    // 건증을 위한 구현
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String username = authentication.getName();
        String password = (String) authentication.getCredentials();
        log.info("username : {}", username);
        log.info("password : {}", password);

        // 이름으로 accountContext 데이터를 가져온다.
        AccountContext accountContext = (AccountContext) userDetailsService.loadUserByUsername(username);
        log.info("password account: {}", accountContext.getAccount().getPassword());
        log.info("password : {}", accountContext.getPassword());

        // 암호가 일치하는 지 확인
        if (!passwordEncoder.matches(password, accountContext.getAccount().getPassword())) {
            log.info("불일치");
            throw new BadCredentialsException("Invalid password");
        }

        FormWebAuthenticationDetails formWebAuthenticationDetails
                = (FormWebAuthenticationDetails) authentication.getDetails();

        String secretKey = formWebAuthenticationDetails.getSecretKey();

        if (secretKey == null || !"secret".equals(secretKey)) {
            throw new InsufficientAuthenticationException("InsufficientAuthenticationException");
        }

        log.info("일치");

        // 비밀버호는 보안상 노출을 하지 않기 위해 null로 넣는다.
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                accountContext.getAccount(), null, accountContext.getAuthorities()
        );

        return authenticationToken;
    }

    // 파라미터로 전달되는 authentication 클래스 타입과 custo.. 타입이 일치할 때 프로바이더 인증 처리를 할 수 있게 한다.
    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
