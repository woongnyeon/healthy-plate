package com.healthy_plate.ingredient.domain.model;

import com.healthy_plate.shared.domain.BaseEntity;
import com.healthy_plate.user.domain.model.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

    @Column(length = 100, nullable = false)
    private String name;

    @Column(name = "name_en", length = 100)
    private String nameEn;

    @Column(name = "calorie")
    private int calorie;

    @Column(name = "serving_size", length = 50)
    private String servingSize;

    @Enumerated(EnumType.STRING)
    @Column(name = "unit", length = 10)
    private IngredientUnit unit;

    @Enumerated(EnumType.STRING)
    @Column(name = "registration_type", length = 20, nullable = false)
    private RegistrationType registrationType = RegistrationType.SYSTEM;

    //등록한 회원
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "registered_by")
    private User registeredBy;

    //검증 여부 (관리자 승인)
    @Column(name = "is_verified", nullable = false)
    private Boolean isVerified = false;

    public Ingredient(
        final String name,
        final String nameEn,
        final int calorie,
        final String servingSize,
        final String unit,
        final RegistrationType registrationType,
        final boolean isVerified,
        final User registeredBy
    ) {
        this.name = name;
        this.nameEn = nameEn;
        this.calorie = calorie;
        this.servingSize = servingSize;
        this.unit = IngredientUnit.fromUnit(unit);
        this.registrationType = registrationType;
        this.registeredBy = registeredBy;
        this.isVerified = false;
    }

    // Batch 로딩용 정적 팩토리 메서드
    public static Ingredient createSystemIngredient(final String name, final int calorie, final String servingSize, final IngredientUnit unit) {
        Ingredient ingredient = new Ingredient();
        ingredient.name = name;
        ingredient.nameEn = null;
        ingredient.calorie = calorie;
        ingredient.servingSize = servingSize;
        ingredient.unit = unit;
        ingredient.registrationType = RegistrationType.SYSTEM;
        ingredient.registeredBy = null;
        ingredient.isVerified = true;
        return ingredient;
    }
}
