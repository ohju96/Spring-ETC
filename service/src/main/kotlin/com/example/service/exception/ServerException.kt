package com.example.service.exception

import org.aspectj.weaver.IntMap

sealed class ServerException(
    val code: Int,
    override val message: String,
): RuntimeException()

data class NotFoundException(
    override val message: String = "사용자가 없습니다.",
): ServerException(404, message)

data class UnauthorizedException(
    override val message: String = "인증 정보가 잘못되었습니다.",
): ServerException(401, message)