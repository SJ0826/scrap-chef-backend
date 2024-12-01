package com.example.scrap_chef.service;

import com.example.scrap_chef.data.ingredient.CreateIngredientInDto;
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

    /**
     * 전체 재료를 조회합니다.
     */
    public GetIngredientsOutDto getIngredients() {
        List<Ingredient> ingredients = ingredientRepository.findAll();

        return new GetIngredientsOutDto(ingredients);
    }

    /**
     * 재료를 생성합니다.
     */
    @Transactional
    public Ingredient createIngredient(CreateIngredientInDto createIngredientInDto) {
        String title = createIngredientInDto.getTitle();
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

    /**
     * 재료를 삭제합니다.
     */
    @Transactional
    public Object deleteIngredient(Long id) {
        log.info(String.valueOf(id));
        Ingredient ingredient = ingredientRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 재료입니다."));
        ingredientRepository.delete(ingredient);
        return null;
    }
}

//    @Transactional
//    public Ingredient updateIngredient(Long id, IngredientUpdateDto ingredientUpdateDto) {
//        Ingredient targetIngredient = ingredientRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 재료입니다."));
//
//        targetIngredient.setTitle(ingredientUpdateDto.getTitle());
//        ingredientRepository.save(targetIngredient);
//
//        return targetIngredient;
//    }