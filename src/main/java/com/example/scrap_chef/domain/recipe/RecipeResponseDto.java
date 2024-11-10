package com.example.scrap_chef.domain.recipe;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class RecipeResponseDto {
    @Setter(onMethod = @__(@JsonSetter(value = "COOKRCP01"))) // json 데이터가 객체로 변환될때 해당 필드의 키의 값을 반환
    @Getter(onMethod = @__(@JsonGetter(value = "recipes"))) // 객체가 json으로 직렬화될 때 해당 필드의 키로 변환
    private CookRcp01 recipes;

    public static class CookRcp01 {
        @Setter(onMethod = @__(@JsonSetter(value = "total_count")))
        @Getter(onMethod = @__(@JsonGetter(value = "totalCount")))
        private String totalCount;

        @Setter(onMethod = @__(@JsonSetter(value = "row")))
        @Getter(onMethod = @__(@JsonGetter(value = "recipe")))
        private List<RecipeDto> recipe;
    }
}

