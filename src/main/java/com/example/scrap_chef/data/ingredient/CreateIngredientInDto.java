package com.example.scrap_chef.data.ingredient;

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
public class CreateIngredientInDto {
    @Schema(description = "재료 이름", example = "APPLE")
    @NotBlank
    private String title;
}
