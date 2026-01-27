package com.healthy_plate.ingredient.domain.model;

import com.healthy_plate.shared.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ingredient")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Ingredient extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ingredient_id")
    private Long id;

    @Column(name = "registered_id")
    private Long registerId;

    @Column(name = "name", length = 100, nullable = false, unique = true)
    private String name;

    @Column(name = "name_en", length = 100)
    private String nameEn;

    @Column(name = "calorie")
    private Integer calorie;

    @Column(name = "serving_size")
    private Double servingSize;

    @Enumerated(EnumType.STRING)
    @Column(name = "unit", length = 10)
    private IngredientUnit unit;

    @Enumerated(EnumType.STRING)
    @Column(name = "registration_type", length = 20, nullable = false)
    private RegistrationType registrationType = RegistrationType.SYSTEM;

    @Column(name = "is_verified", nullable = false)
    private Boolean isVerified;


    public Ingredient(
        final String name,
        final String nameEn,
        final Integer calorie,
        final Double servingSize,
        final IngredientUnit unit,
        final RegistrationType registrationType,
        final boolean isVerified,
        final Long registerId
        ) {
        this.name = name;
        this.nameEn = nameEn;
        this.calorie = calorie;
        this.servingSize = servingSize;
        this.unit = unit;
        this.registrationType = registrationType;
        this.isVerified = isVerified;
        this.registerId = registerId;
    }

    public static Ingredient createSystemIngredient(
        final String name,
        final Integer calorie,
        final Double servingSize,
        final IngredientUnit unit
    ) {
        return new Ingredient(
            name,
            null,
            calorie,
            servingSize,
            unit,
            RegistrationType.SYSTEM,
            true,
            null
        );
    }

    public void updateIngredient(
        final String name,
        final String nameEn,
        final Double servingSize,
        final IngredientUnit unit,
        final Integer calorie
    ) {
        if (name != null) {
            this.name = name;
        }
        if (nameEn != null) {
            this.nameEn = nameEn;
        }
        if (servingSize != null) {
            this.servingSize = servingSize;
        }
        if (unit != null) {
            this.unit = unit;
        }
        if (calorie != null) {
            this.calorie = calorie;
        }
    }
}
