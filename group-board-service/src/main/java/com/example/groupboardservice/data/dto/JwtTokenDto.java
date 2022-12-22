package com.example.groupboardservice.data.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "JWT 토큰 객체")
public class JwtTokenDto {

    @Schema(description = "Access Token")
    private String accessToken;
    @Schema(description = "Refresh Token")
    private String refreshToken;

    public static JwtTokenDto accessToken(String accessToken) {
        JwtTokenDto jwtTokenDto = new JwtTokenDto();
        jwtTokenDto.setAccessToken(accessToken);
        return jwtTokenDto;
    }

    public static JwtTokenDto refreshToken(String refreshToken) {
        JwtTokenDto jwtTokenDto = new JwtTokenDto();
        jwtTokenDto.setRefreshToken(refreshToken);
        return jwtTokenDto;
    }

    public static JwtTokenDto JwtToken(String accessToken, String refreshToken) {
        JwtTokenDto jwtTokenDto = new JwtTokenDto();
        jwtTokenDto.setAccessToken(accessToken);
        jwtTokenDto.setRefreshToken(refreshToken);
        return jwtTokenDto;
    }

}
