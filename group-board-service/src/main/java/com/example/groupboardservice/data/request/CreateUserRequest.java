package com.example.groupboardservice.data.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(defaultValue = "유저 생성 요청 객체")
public class CreateUserRequest {

    @Schema(defaultValue = "유저 아이디", example = "ohjuhyeon")
    private String id;

    @Schema(defaultValue = "이메일", example = "ohjuhyeon@email.com")
    private String email;

    @Schema(defaultValue = "비밀번호", example = "ohju1234")
    private String password;
}
