package com.healthy_plate.ingredient.infrastructure.repository;

import com.healthy_plate.ingredient.domain.model.Ingredient;
import com.healthy_plate.ingredient.domain.repository.IngredientRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaIngredientRepository extends JpaRepository<Ingredient, Long>, IngredientRepository {

}
