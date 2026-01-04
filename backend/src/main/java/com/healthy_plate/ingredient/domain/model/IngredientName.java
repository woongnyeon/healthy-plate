package com.healthy_plate.ingredient.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class IngredientName {

    @Column(name = "name", length = 100, nullable = false, unique = true)
    private String value;

    private IngredientName(final String value) {
        this.value = value;
    }

    public static IngredientName of(final String value) {
        validateName(value);
        return new IngredientName(value);
    }

    private static void validateName(final String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("식재료명은 공백일 수 없습니다.");
        }
        if (value.length() > 100) {
            throw new IllegalArgumentException("식재료명은 100자 이하여야 합니다.");
        }
    }
}
