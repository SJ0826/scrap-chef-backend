package com.example.scrap_chef.domain.recipe;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class RecipeOpenApiResponseDto {
    @Setter(onMethod = @__(@JsonSetter(value = "COOKRCP01")))
    @Getter(onMethod = @__(@JsonGetter(value = "recipes")))
    private CookRcp01 recipes;

    public static class CookRcp01 {
        @Setter(onMethod = @__(@JsonSetter(value = "total_count")))
        @Getter(onMethod = @__(@JsonGetter(value = "totalCount")))
        private String totalCount;

        @Setter(onMethod = @__(@JsonSetter(value = "row")))
        @Getter(onMethod = @__(@JsonGetter(value = "recipe")))
        private List<RecipeOpenApiDto> recipe;


    }
}
