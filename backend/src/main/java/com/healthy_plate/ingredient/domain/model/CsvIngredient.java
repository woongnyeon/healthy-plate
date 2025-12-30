package com.healthy_plate.ingredient.domain.model;

public record CsvIngredient(
    String name,
    String servingSize,
    String unit,
    String calorie
) {

}