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
public class ServingSize {

    @Column(name = "serving_size")
    private Double value;

    private ServingSize(final Double value) {
        this.value = value;
    }

    public static ServingSize of(final Double value) {
        validateServingSize(value);
        return new ServingSize(value);
    }

    private static void validateServingSize(final Double value) {
        if (value != null && value <= 0) {
            throw new IllegalArgumentException("1회 제공량은 0보다 커야 합니다.");
        }
    }
}
