package com.example.scrap_chef.service;

import com.example.scrap_chef.domain.Ingredient;
import com.example.scrap_chef.domain.IngredientUpdateDto;
import com.example.scrap_chef.repository.IngredientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Service
@RequiredArgsConstructor
public class IngredientService {

    private final IngredientRepository ingredientRepository;

    public List<Ingredient> getIngredients() {
        return ingredientRepository.findAll();
    }

    public Ingredient createIngredient(String title) {
        boolean isDuplicate = ingredientRepository.existsByTitle(title);
        if (isDuplicate) {
            throw new IllegalArgumentException("중복된 재료입니다.");
        }

        Ingredient newIngredient = Ingredient.builder()
                .title(title)
                .createdAt(LocalDateTime.now().toString())
                .build();
        ingredientRepository.save(newIngredient);

        return newIngredient;
    }

    public Ingredient updateIngredient(Long id, IngredientUpdateDto ingredientUpdateDto) {
        Ingredient targetIngredient = ingredientRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 재료입니다."));

        targetIngredient.setTitle(ingredientUpdateDto.getTitle());

        return targetIngredient;
    }

    public void deleteIngredient(Long id) {
        ingredientRepository.deleteById(id);
    }
}
