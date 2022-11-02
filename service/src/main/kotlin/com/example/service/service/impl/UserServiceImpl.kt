package com.example.service.service.impl

import com.example.service.data.dto.UserDTO
import com.example.service.data.mapper.UserMapper
import com.example.service.repository.UserRepository
import com.example.service.service.UserService
import mu.KotlinLogging
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    private val userMapper: UserMapper,
    private val userRepository: UserRepository,

): UserService {

    private val logger = KotlinLogging.logger {}

    override fun createUsers(userDTO: UserDTO) {
        logger.info("### .createUsers start")
        userRepository.save(userMapper.toEntity(userDTO));
    }

    override fun deleteUsers(userDTO: UserDTO) {
        logger.info("### .deleteUsers start")
    }
}