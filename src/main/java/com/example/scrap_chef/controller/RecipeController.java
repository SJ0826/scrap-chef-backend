package com.example.scrap_chef.controller;

import com.example.scrap_chef.domain.RecipeResponseDto;
import com.example.scrap_chef.dto.ApiResponse;
import com.example.scrap_chef.service.RecipeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/recipes")
public class RecipeController {

    private final RecipeService recipeService;
    // 레시피 목록 조회

    @GetMapping
    public Mono<ApiResponse<RecipeResponseDto>> getRecipesData(@RequestParam(required = false) List<String> ingredients, @RequestParam(defaultValue = "1") int page) {
        System.out.println("check");
        return recipeService.getRecipes(ingredients, page);

    }
}
