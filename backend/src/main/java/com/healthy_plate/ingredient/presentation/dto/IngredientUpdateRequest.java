package com.healthy_plate.ingredient.presentation.dto;

import jakarta.validation.constraints.Size;

public record IngredientUpdateRequest(

    @Size(max = 100, message = "식재료명은 100자 이하여야 합니다.")
    String name,

    @Size(max = 100, message = "식재료명은 100자 이하여야 합니다.")
    String nameEn,

    Double servingSize,

    String unit,

    Integer calorie
) {

}
