package com.healthy_plate.ingredient.domain.repository;

import com.healthy_plate.ingredient.domain.model.Ingredient;

public interface IngredientRepository {

    void save(Ingredient ingredient);

    boolean existByName(String name);

    long count();

    void deleteAll();
}
