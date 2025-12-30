package com.healthy_plate.ingredient.presentation.dto;

import com.healthy_plate.ingredient.domain.model.Ingredient;

public record IngredientResponse(String name, int calorie) {

    public static IngredientResponse from(final Ingredient ingredient) {
        return new IngredientResponse(ingredient.getName(), ingredient.getCalorie());
    }
}
