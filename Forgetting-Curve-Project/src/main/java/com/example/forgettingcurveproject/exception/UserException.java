package com.example.forgettingcurveproject.exception;

import com.example.forgettingcurveproject.exception.result.UserExceptionResult;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserException extends RuntimeException{
    private final UserExceptionResult errorResult;
}
