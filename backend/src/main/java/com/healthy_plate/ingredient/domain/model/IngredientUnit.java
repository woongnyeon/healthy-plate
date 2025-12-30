package com.healthy_plate.ingredient.domain.model;

import lombok.Getter;

@Getter
public enum IngredientUnit {
    G("g", "그램"),
    KG("kg", "킬로그램"),
    ML("ml", "밀리리터"),
    L("L", "리터"),
    EA("개", "개"),
    TBSP("큰술", "큰술"),
    TSP("작은술", "작은술"),
    CUP("컵", "컵");

    private final String unit;
    private final String description;

    IngredientUnit(final String unit, final String description) {
        this.unit = unit;
        this.description = description;
    }

    public static IngredientUnit fromUnit(String unit) {
        for (IngredientUnit ingredientUnit : values()) {
            if (ingredientUnit.unit.equals(unit)) {
                return ingredientUnit;
            }
        }
        throw new IllegalArgumentException("Unknown unit: " + unit);
    }
}
