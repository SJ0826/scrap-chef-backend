package com.example.scrap_chef.data.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 최대 상속관계의 클래스에서만 접근 가능
public class SignupInDto {

    @Schema(description = "이름", example = "홍길동")
    @NotBlank
    private String username;

    @Schema(description = "비밀번호", example = "qwer1234!")
    @NotBlank
    private String password;

}
