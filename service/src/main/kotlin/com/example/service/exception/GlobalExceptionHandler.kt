package com.example.service.exception

import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(ServerException::class)
    fun handleServerException(e: ServerException): ErrorResponse {
        return ErrorResponse(code = e.code, message = e.message)
    }
}