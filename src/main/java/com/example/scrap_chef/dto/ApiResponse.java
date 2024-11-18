package com.example.scrap_chef.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    @Schema(description = "응답코드", example = "0")
    private int code;

    @Schema(description = "응답메시지", example = "success")
    private String message;
    private T data;

}
