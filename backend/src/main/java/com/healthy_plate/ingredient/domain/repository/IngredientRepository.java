package com.healthy_plate.ingredient.domain.repository;

import com.healthy_plate.ingredient.application.dto.IngredientWithUserDto;
import com.healthy_plate.ingredient.domain.model.Ingredient;
import java.util.List;
import java.util.Optional;

public interface IngredientRepository {

    Ingredient save(Ingredient ingredient);

    Optional<Ingredient> findById(Long id);

    List<Ingredient> findByNameContaining(String name);

    long count();

    boolean existsByName(String name);

    void deleteById(Long id);

    void deleteAll();

    List<IngredientWithUserDto> findByUserIdWithUserInfo(Long userId);

}
