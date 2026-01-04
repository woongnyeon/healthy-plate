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
public class Calorie {

    @Column(name = "calorie")
    private Integer value;

    private Calorie(final Integer value) {
        this.value = value;
    }

    public static Calorie of(final Integer value) {
        validateCalorie(value);
        return new Calorie(value);
    }

    private static void validateCalorie(final Integer value) {
        if (value != null && value < 0) {
            throw new IllegalArgumentException("칼로리는 음수일 수 없습니다.");
        }
    }
}
