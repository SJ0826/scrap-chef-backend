package com.example.scrap_chef.data.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignInInDto {

    @Schema(description = "아이디", example = "test")
    @NotBlank
    private String loginId;

    @Schema(description = "비밀번호", example = "qwer1234!")
    @NotBlank
    private String password;
}
