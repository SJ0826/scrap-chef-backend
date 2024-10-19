package com.example.scrap_chef.controller;

import com.example.scrap_chef.domain.Ingredient;
import com.example.scrap_chef.domain.IngredientUpdateDto;
import com.example.scrap_chef.service.IngredientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/ingredient")
public class IngredientController {

    private final IngredientService ingredientService;

    // 전체 재료 조회
    @GetMapping
    public List<Ingredient> getIngredient() {
        return ingredientService.getIngredients();
    }

    // 특정 재료 조회


    // 재료 생성
    @PostMapping
    public ResponseEntity<?> createIngredient(@RequestParam("title") String title) { // TODO: 타입을 모호하게 줘도 되는지 물어보기
        try {
            Ingredient createdIngredient = ingredientService.createIngredient(title);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdIngredient);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // 재료 수정
    @PatchMapping("/{id}")
    public Ingredient updateIngredient(@PathVariable("id") Long id, @RequestBody IngredientUpdateDto updateRequest) {
        return ingredientService.updateIngredient(id, updateRequest);
    }

    // 재료 삭제
    @DeleteMapping("/{id}")
    public void deleteIngredient(@PathVariable("id") Long id) {
        ingredientService.deleteIngredient(id);
    }
}
