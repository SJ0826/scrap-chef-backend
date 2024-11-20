package com.example.scrap_chef.data.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class CheckDuplicateUserOutDto {

    @Schema(description = "아이디 중복 여부", example = "false")
    private String isDuplicate;
}
