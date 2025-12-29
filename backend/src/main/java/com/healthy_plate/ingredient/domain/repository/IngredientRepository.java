package com.healthy_plate.ingredient.domain.repository;

import com.healthy_plate.ingredient.domain.model.Ingredient;

public interface IngredientRepository {

    Ingredient save(Ingredient ingredient);

    long count();
}
