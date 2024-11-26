package com.example.scrap_chef.service;

import com.example.scrap_chef.data.ingredient.GetIngredientsOutDto;
import com.example.scrap_chef.entity.Ingredient;
import com.example.scrap_chef.data.ingredient.IngredientUpdateDto;
import com.example.scrap_chef.repository.IngredientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class IngredientService {

    private final IngredientRepository ingredientRepository;

    public GetIngredientsOutDto getIngredients() {
        List<Ingredient> ingredients = ingredientRepository.findAll();

        return new GetIngredientsOutDto(ingredients);
    }

    @Transactional // 작업을 모두 성공하거나, 모두 실패하도록 보장
    public Ingredient createIngredient(String title) {
        boolean isDuplicate = ingredientRepository.existsByTitle(title);
        if (isDuplicate) {
            throw new IllegalArgumentException("중복된 재료입니다.");
        }

        Ingredient newIngredient = Ingredient.builder()
                .title(title)
                .build();
        ingredientRepository.save(newIngredient);

        return newIngredient;
    }
//
//    @Transactional
//    public Ingredient updateIngredient(Long id, IngredientUpdateDto ingredientUpdateDto) {
//        Ingredient targetIngredient = ingredientRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 재료입니다."));
//
//        targetIngredient.setTitle(ingredientUpdateDto.getTitle());
//        ingredientRepository.save(targetIngredient);
//
//        return targetIngredient;
//    }

    @Transactional
    public void deleteIngredient(Long id) {
        Ingredient ingredient = ingredientRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 재료입니다."));
        ingredientRepository.delete(ingredient);
    }
}
