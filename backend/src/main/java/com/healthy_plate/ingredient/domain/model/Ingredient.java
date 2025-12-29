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

    @Enumerated(EnumType.STRING)
    @Column(name = "registration_type", length = 20, nullable = false)
    private RegistrationType registrationType = RegistrationType.SYSTEM;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "registered_by")
    private User registeredBy;

    @Column(name = "is_verified", nullable = false)
    private Boolean isVerified = false;

    public Ingredient(
        final String name,
        final String nameEn,
        final RegistrationType registrationType,
        final User registeredBy
    ) {
        this.name = name;
        this.nameEn = nameEn;
        this.registrationType = registrationType;
        this.registeredBy = registeredBy;
        this.isVerified = false;
    }

    public void verify() {
        this.isVerified = true;
    }

    // Batch 로딩용 정적 팩토리 메서드
    public static Ingredient createSystemIngredient(final String name, final int calorie) {
        Ingredient ingredient = new Ingredient();
        ingredient.name = name;
        ingredient.calorie = calorie;
        ingredient.registrationType = RegistrationType.SYSTEM;
        ingredient.isVerified = true;
        ingredient.registeredBy = null;
        ingredient.nameEn = null;
        return ingredient;
    }
}
