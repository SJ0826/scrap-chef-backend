package com.example.scrap_chef.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.scrap_chef.domain.ingredient.Ingredient;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    boolean existsByTitle(String title);

}