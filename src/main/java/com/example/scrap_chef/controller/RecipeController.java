package com.example.scrap_chef.controller;

import com.example.scrap_chef.service.RecipeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/recipes")
public class RecipeController {

    private final RecipeService recipeService;
    // 레시피 목록 조회

//    @GetMapping
//    public Mono<ApiResponse<RecipeResponseDto>> getRecipesData(@RequestParam(required = false) List<String> ingredients, @RequestParam(defaultValue = "1") int page) {
//        return recipeService.getRecipes(ingredients, page);
//    }
}
