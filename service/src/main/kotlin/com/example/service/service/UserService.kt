package com.example.service.service

import com.example.service.data.dto.UserDTO

interface UserService {

    fun createUsers(userDTO: UserDTO)
    fun deleteUsers(userDTO: UserDTO)
}