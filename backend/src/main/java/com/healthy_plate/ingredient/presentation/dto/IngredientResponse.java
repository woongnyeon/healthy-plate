package com.healthy_plate.ingredient.presentation.dto;

import com.healthy_plate.ingredient.domain.model.Ingredient;

public record IngredientResponse(String name, Integer calorie) {

    public static IngredientResponse from(final Ingredient ingredient) {
        return new IngredientResponse(
            ingredient.getName().getValue(),
            ingredient.getCalorie().getValue()
        );
    }
}
