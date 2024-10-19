package com.example.scrap_chef.service;

import com.example.scrap_chef.domain.Ingredient;
import com.example.scrap_chef.domain.IngredientUpdateDto;
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

    private final List<Ingredient> ingredients = new ArrayList<>();
    private AtomicLong idCounter = new AtomicLong(1); // ID 자동 증가


    public List<Ingredient> getIngredients() {
        return new ArrayList<>(ingredients);
    }

    public Ingredient createIngredient(String title) {
        boolean isDuplicate = ingredients.stream().anyMatch(ingredient -> ingredient.getTitle().equals(title));
        if (isDuplicate) {
            throw new IllegalArgumentException("중복된 재료입니다.");
        }

        Ingredient newIngredient = Ingredient.builder()
                .id(idCounter.getAndIncrement())
                .title(title)
                .createdAt(LocalDateTime.now().toString())
                .build();
        ingredients.add(newIngredient);

        return newIngredient;
    }

    public Ingredient updateIngredient(Long id, IngredientUpdateDto ingredientUpdateDto) {
        Ingredient targetIngredient = ingredients.stream()
                .filter(ingredient -> ingredient.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 재료입니다."));

        targetIngredient.setTitle(ingredientUpdateDto.getTitle());

        return targetIngredient;
    }

    public void deleteIngredient(Long id) {
        ingredients.removeIf(ingredient -> ingredient.getId().equals(id));
    }
}
