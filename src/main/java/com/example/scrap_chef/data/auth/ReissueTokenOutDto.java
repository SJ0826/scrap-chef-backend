package com.example.scrap_chef.data.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
public class ReissueTokenOutDto {
    private String accessToken;
    private String refreshToken;
}
