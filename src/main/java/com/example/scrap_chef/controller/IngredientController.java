package com.example.scrap_chef.controller;

import com.example.scrap_chef.data.ingredient.CreateIngredientInDto;
import com.example.scrap_chef.data.ingredient.GetIngredientsOutDto;
import com.example.scrap_chef.service.IngredientService;
import com.example.scrap_chef.util.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.scrap_chef.util.ResponseUtil.success;


@Slf4j
@Tag(name = "재료")
@RestController
@RequestMapping("/v1/ingredients")
@RequiredArgsConstructor
public class IngredientController {

    private final IngredientService ingredientService;


    @Operation(summary = "전체 재료 조회")
    @GetMapping
    public ResponseEntity<ApiResponse<GetIngredientsOutDto>> getIngredients() {
        return success(ingredientService.getIngredients());
    }

    @Operation(summary = "재료 생성")
    @PostMapping
    public ResponseEntity<ApiResponse<Object>> createIngredient(@RequestBody CreateIngredientInDto createIngredientInDto) {
        return success(ingredientService.createIngredient(createIngredientInDto));
    }

    @Operation(summary = "재료 삭제")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> deleteIngredient(@PathVariable("id") Long id) {
        return success(ingredientService.deleteIngredient(id));
    }

}
