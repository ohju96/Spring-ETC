package com.example.service.data.mapper

import com.example.service.data.domain.User
import com.example.service.data.dto.UserDTO
import org.mapstruct.Mapper
import org.mapstruct.ReportingPolicy

@Mapper(componentModel = "string", uses = [], unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface UserMapper: EntityMapper<UserDTO, User>