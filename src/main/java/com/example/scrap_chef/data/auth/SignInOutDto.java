package com.example.scrap_chef.data.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SignInOutDto {

    @Schema(description = "엑세스 토큰", example = "ACCESS_TOKEN")
    private String accessToken;

    @Schema(description = "리프레시 토큰", example = "REFRESH_TOKEN")
    private String refreshToken;
}
