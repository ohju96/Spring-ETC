package com.example.service.controller

import com.example.service.data.dto.UserDTO
import com.example.service.service.UserService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/v1")
@RestController
class UserController(
        private val userService: UserService,
) {

    @PostMapping
    fun createUsers(userDTO: UserDTO) {
        userService.createUsers(userDTO)
    }

    @DeleteMapping
    fun deleteUsers(userId: String) {
        userService.deleteUsers(userId)
    }

}