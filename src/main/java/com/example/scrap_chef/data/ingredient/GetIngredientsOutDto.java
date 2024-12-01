package com.example.scrap_chef.data.ingredient;

import com.example.scrap_chef.entity.Ingredient;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class GetIngredientsOutDto {

    @Schema(
            description = "재료 목록",
            example = "[{ \"id\": 1, \"title\": \"Salt\", \"createdAt\": \"2024-11-26T12:48:32.537Z\", \"updatedAt\": \"2024-11-26T12:48:32.537Z\" }]"
    )

    private List<Ingredient> ingredients;
}
