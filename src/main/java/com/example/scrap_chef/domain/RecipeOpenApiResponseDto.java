package com.example.scrap_chef.domain;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class RecipeOpenApiResponseDto {
    @Setter(onMethod = @__(@JsonSetter(value = "COOKRCP01")))
    @Getter(onMethod = @__(@JsonGetter(value = "recipes")))
    private RecipeOpenApiResponseDto.CookRcp01 recipes;

    public static class CookRcp01 {
        @Setter(onMethod = @__(@JsonSetter(value = "total_count")))
        @Getter(onMethod = @__(@JsonGetter(value = "totalCount")))
        private String totalCount;

        @Setter(onMethod = @__(@JsonSetter(value = "row")))
        @Getter(onMethod = @__(@JsonGetter(value = "recipe")))
        private List<RecipeOpenApiDto> recipe;

        @Setter(onMethod = @__(@JsonSetter(value = "RESULT")))
        @Getter(onMethod = @__(@JsonGetter(value = "result")))
        private RecipeResponseDto.CookRcp01.Result result;

        public static class Result {
            @Setter(onMethod = @__(@JsonSetter(value = "MSG")))
            @Getter(onMethod = @__(@JsonGetter(value = "message")))
            private String message;

            @Setter(onMethod = @__(@JsonSetter(value = "CODE")))
            @Getter(onMethod = @__(@JsonGetter(value = "code")))
            private String code;
        }


    }
}
