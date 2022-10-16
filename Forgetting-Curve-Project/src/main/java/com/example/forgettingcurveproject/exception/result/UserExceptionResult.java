package com.example.forgettingcurveproject.exception.result;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserExceptionResult {
    DUPLICATE_USER_EMAIL(HttpStatus.BAD_REQUEST, "중복된 사용자 이메일입니다."),
    UNAUTHORIZED_REQUEST(HttpStatus.BAD_REQUEST, "해당 정보에 접근 권한이 없습니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자 정보를 찾을 수 없습니다."),
    UNKNOWN_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "Unknown Exception"),
    USER_PASSWORD_NOT_MATCHED(HttpStatus.BAD_REQUEST, "비밀번호를 확인해주세요."),
    EXPIRED_JWT_TOKEN(HttpStatus.FORBIDDEN, "만료된 토큰 입니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
