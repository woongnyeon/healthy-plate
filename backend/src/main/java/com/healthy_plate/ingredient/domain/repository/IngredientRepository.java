package com.healthy_plate.ingredient.domain.repository;

import com.healthy_plate.ingredient.domain.model.Ingredient;
import java.util.List;
import java.util.Optional;

public interface IngredientRepository {

    void save(Ingredient ingredient);

    boolean existByName(String name);

    long count();

    void deleteAll();

    Optional<List<Ingredient>> findByName(String name);
}
