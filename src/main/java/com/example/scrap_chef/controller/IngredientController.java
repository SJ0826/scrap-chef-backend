package com.example.scrap_chef.controller;

import com.example.scrap_chef.service.IngredientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


// TODO: 에러메세지는 컨트롤러에서 관리할까 서비스에서 관리할까

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/ingredients")
public class IngredientController {

    private final IngredientService ingredientService;

//    // 전체 재료 조회
//    @GetMapping
//    public ResponseEntity<ApiResponse<List<Ingredient>>> getIngredient() {
//        try {
//            List<Ingredient> ingredients = ingredientService.getIngredients();
//            ApiResponse<List<Ingredient>> response = new ApiResponse<>("success", ingredients);
//            return ResponseEntity.status(HttpStatus.OK).body(response);
//        } catch (IllegalStateException e) {
//            ApiResponse<List<Ingredient>> response = new ApiResponse<>("No ingredients found", null);
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
//        }
//
//    }
//
//    // 특정 재료 조회
//
//
//    // 재료 생성
//    @PostMapping
//    public ResponseEntity<ApiResponse<Ingredient>> createIngredient(@RequestParam("title") String title) {
//        try {
//            Ingredient createdIngredient = ingredientService.createIngredient(title);
//            ApiResponse<Ingredient> response = new ApiResponse<>("success", createdIngredient);
//            return ResponseEntity.status(HttpStatus.CREATED).body(response);
//        } catch (IllegalArgumentException e) {
//            ApiResponse<Ingredient> response = new ApiResponse<>(e.getMessage(), null);
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
//        }
//    }
//
//    // 재료 수정
//    @PatchMapping("/{id}")
//    public ResponseEntity<ApiResponse<Ingredient>> updateIngredient(@PathVariable("id") Long id, @RequestBody IngredientUpdateDto updateRequest) {
//        try {
//            Ingredient updatedIngredient = ingredientService.updateIngredient(id, updateRequest);
//            ApiResponse<Ingredient> response = new ApiResponse<>("success", updatedIngredient);
//            return ResponseEntity.status(HttpStatus.OK).body(response);
//        } catch (IllegalArgumentException e) {
//            ApiResponse<Ingredient> response = new ApiResponse<>(e.getMessage(), null);
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
//        }
//    }
//
//    // 재료 삭제
//    @DeleteMapping("/{id}")
//    public ResponseEntity<ApiResponse<Void>> deleteIngredient(@PathVariable("id") Long id) {
//        try {
//            ingredientService.deleteIngredient(id);
//            ApiResponse<Void> response = new ApiResponse<>("success", null);
//            return ResponseEntity.ok(response);
//        } catch (IllegalArgumentException e) {
//            ApiResponse<Void> response = new ApiResponse<>("존재하지 않는 재료입니다.", null);
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
//        }
//    }
}
