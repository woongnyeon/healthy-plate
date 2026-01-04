package com.healthy_plate.ingredient.domain.model;

import com.healthy_plate.shared.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
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

    @Embedded
    private IngredientName name;

    @Column(name = "name_en", length = 100)
    private String nameEn;

    @Embedded
    private Calorie calorie;

    @Embedded
    private ServingSize servingSize;


    @Enumerated(EnumType.STRING)
    @Column(name = "unit", length = 10)
    private IngredientUnit unit;

    @Enumerated(EnumType.STRING)
    @Column(name = "registration_type", length = 20, nullable = false)
    private RegistrationType registrationType = RegistrationType.SYSTEM;

    //등록한 회원
    @Embedded
    private RegisterId registerId;

    //검증 여부 (관리자 승인)
    @Column(name = "is_verified", nullable = false)
    private Boolean isVerified = false;

    public Ingredient(
        final IngredientName name,
        final String nameEn,
        final Calorie calorie,
        final ServingSize servingSize,
        final IngredientUnit unit,
        final RegistrationType registrationType,
        final boolean isVerified,
        final RegisterId registerId
    ) {
        this.name = name;
        this.nameEn = nameEn;
        this.calorie = calorie;
        this.servingSize = servingSize;
        this.unit = unit;
        this.registrationType = registrationType;
        this.registerId = registerId;
        this.isVerified = false;
    }

    // Batch 로딩용 정적 팩토리 메서드
    public static Ingredient createSystemIngredient(
        final IngredientName name,
        final Calorie calorie,
        final ServingSize servingSize,
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
        final IngredientName name,
        final String nameEn,
        final ServingSize servingSize,
        final IngredientUnit unit,
        final Calorie calorie
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
