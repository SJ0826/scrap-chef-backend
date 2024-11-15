package com.example.scrap_chef.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;

import static io.swagger.v3.oas.models.security.SecurityScheme.In.HEADER;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;


@Slf4j
@Configuration
@RequiredArgsConstructor
@Profile({"local", "dev"})
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        SecurityRequirement securityRequirement = new SecurityRequirement().addList("Authorization");
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("Authorization", // 보안 요구사항 추가
                                new SecurityScheme()
                                        .type(Type.HTTP)
                                        .in(HEADER)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")))
                .addSecurityItem(securityRequirement)
                .info(new Info());
    }

    private Info info() {
        return new Info()
                .title("Scrap Chef API")
                .description("냉장고 탈탈 API 문서입니다.")
                .version("1.0.0");
    }
}
