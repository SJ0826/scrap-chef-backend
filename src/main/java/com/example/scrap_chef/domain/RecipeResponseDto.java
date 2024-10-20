package com.example.scrap_chef.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecipeResponseDto {
    private String id;
    private String recipeName;
    private String ingredients;
    private String instructions;
}
