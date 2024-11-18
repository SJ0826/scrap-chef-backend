package com.example.scrap_chef.data.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@Builder
public class SignupInDto {

    @Schema(description = "이름", example = "홍길동")
    private String username;

    @Schema(description = "비밀번호", example = "qwer1234!")
    @NotBlank
    private String password;

}
