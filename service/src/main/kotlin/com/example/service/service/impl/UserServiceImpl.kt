package com.example.service.service.impl

import com.example.service.data.dto.UserDTO
import com.example.service.data.mapper.UserMapper
import com.example.service.repository.UserRepository
import com.example.service.service.UserService
import mu.KotlinLogging
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    private val userMapper: UserMapper, // 정상 동작을 하는 지 체크해 볼 필요가 있다.
    private val userRepository: UserRepository,

): UserService {

    private val logger = KotlinLogging.logger {}

    override fun createUsers(userDTO: UserDTO) {
        logger.info("### .createUsers start")
        userRepository.save(userMapper.toEntity(userDTO));
    }

    override fun deleteUsers(userId: String) {
        logger.info("### .deleteUsers start")

    }

    override fun getUser(userId: String) {
        
    }
}